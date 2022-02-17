package com.rom.axon.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

    public CreateOrderCommand(String orderId) {
        this.orderId = orderId;
    }
}
