package com.rom.axon.exceptions;

public class DuplicateOrderLineException extends Throwable {
    private final String productId;

    public DuplicateOrderLineException(String productId) {
        this.productId = productId;
    }

}
