package com.rom.axon.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
public class ConfirmOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

    public ConfirmOrderCommand(String orderId) {
        this.orderId = orderId;
    }
}
