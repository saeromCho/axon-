package com.rom.axon.commands;

import lombok.Getter;

@Getter
public class AddProductCommand {
    private final String orderId;
    private final String productId;

    public AddProductCommand(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
