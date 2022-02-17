package com.rom.axon.events;

import lombok.Getter;

@Getter
public class ProductAddedEvent {
    private final String orderId;
    private final String productId;

    public ProductAddedEvent(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
