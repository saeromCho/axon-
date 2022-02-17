package com.rom.axon.commands;

import lombok.Getter;

@Getter
public class IncrementProductCountCommand {
    private final String orderId;
    private final String productId;

    public IncrementProductCountCommand(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
