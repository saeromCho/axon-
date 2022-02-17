package com.rom.axon.commandModel;

import com.rom.axon.commands.DecrementProductCountCommand;
import com.rom.axon.commands.IncrementProductCountCommand;
import com.rom.axon.events.OrderConfirmedEvent;
import com.rom.axon.events.ProductCountDecrementedEvent;
import com.rom.axon.events.ProductCountIncrementedEvent;
import com.rom.axon.events.ProductRemovedEvent;
import com.rom.axon.exceptions.OrderAlreadyConfirmedException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class OrderLine {
    @EntityId
    private final String productId;
    private Integer count;
    private boolean orderConfirmed;

    public OrderLine(String productId) {
        this.productId = productId;
        this.count = 1;
    }

    @CommandHandler
    public void handle(IncrementProductCountCommand command) throws OrderAlreadyConfirmedException {
        if (orderConfirmed) {
            throw new OrderAlreadyConfirmedException(command.getOrderId());
        }

        apply(new ProductCountIncrementedEvent(command.getOrderId(), productId));
    }

    @CommandHandler
    public void handle(DecrementProductCountCommand command) throws OrderAlreadyConfirmedException {
        if (orderConfirmed) {
            throw new OrderAlreadyConfirmedException(command.getOrderId());
        }

        if (count <= 1) {
            apply(new ProductRemovedEvent(command.getOrderId(), productId));
        } else {
            apply(new ProductCountDecrementedEvent(command.getOrderId(), productId));
        }
    }

    @EventSourcingHandler
    public void on(ProductCountIncrementedEvent event) {
        this.count++;
    }

    @EventSourcingHandler
    public void on(ProductCountDecrementedEvent event) {
        this.count--;
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        this.orderConfirmed = true;
    }

}
