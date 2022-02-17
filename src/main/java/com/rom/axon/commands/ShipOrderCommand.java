package com.rom.axon.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class ShipOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

    public ShipOrderCommand(String orderId) {
        this.orderId = orderId;
    }
}
