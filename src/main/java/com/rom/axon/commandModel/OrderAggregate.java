package com.rom.axon.commandModel;

import com.rom.axon.commands.AddProductCommand;
import com.rom.axon.commands.ConfirmOrderCommand;
import com.rom.axon.commands.CreateOrderCommand;
import com.rom.axon.commands.ShipOrderCommand;
import com.rom.axon.events.*;
import com.rom.axon.exceptions.DuplicateOrderLineException;
import com.rom.axon.exceptions.OrderAlreadyConfirmedException;
import com.rom.axon.exceptions.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @AggregateMember
    private Map<String, OrderLine> orderLines;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void handle(AddProductCommand command) throws OrderAlreadyConfirmedException, DuplicateOrderLineException {
        if (orderConfirmed) {
            throw new OrderAlreadyConfirmedException(orderId);
        }

        String productId = command.getProductId();
        if (orderLines.containsKey(productId)) {
            throw new DuplicateOrderLineException(productId);
        }
        apply(new ProductAddedEvent(orderId, productId));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        if (orderConfirmed) {
            return;
        }

        apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) throws UnconfirmedOrderException {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }

        apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.orderConfirmed = false;
        this.orderLines = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        this.orderConfirmed = true;
    }

    @EventSourcingHandler
    public void on(ProductAddedEvent event) {
        String productId = event.getProductId();
        this.orderLines.put(productId, new OrderLine(productId));
    }

    @EventSourcingHandler
    public void on(ProductRemovedEvent event) {
        this.orderLines.remove(event.getProductId());
    }

    protected OrderAggregate() {
        // Required by Axon to build a default Aggregate prior to Event Sourcing
    }
}
