package com.rom.axon.events;

import lombok.Getter;

@Getter
public class ProductCountDecrementedEvent {
    private final String orderId;
    private final String productId;

    public ProductCountDecrementedEvent(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
