package com.rom.axon.events;

import lombok.Getter;

@Getter
public class ProductCountIncrementedEvent {
    private final String orderId;
    private final String productId;


    public ProductCountIncrementedEvent(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
}
