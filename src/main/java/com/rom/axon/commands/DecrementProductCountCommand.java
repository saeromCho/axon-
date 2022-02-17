package com.rom.axon.commands;

import lombok.Getter;

@Getter
public class DecrementProductCountCommand {
    private final String orderId;
    private final String productId;

    public DecrementProductCountCommand(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
