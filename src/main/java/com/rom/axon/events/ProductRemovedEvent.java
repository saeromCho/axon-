package com.rom.axon.events;

import lombok.Getter;

@Getter
public class ProductRemovedEvent {
    private final String orderId;
    private final String productId;

    public ProductRemovedEvent(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

}
