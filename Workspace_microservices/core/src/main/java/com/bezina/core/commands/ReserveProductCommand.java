package com.bezina.core.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ReserveProductCommand {
    @TargetAggregateIdentifier
    public final String productId;
    public final int quantity;
    public final String orderId;
    public final String userId;

    public ReserveProductCommand(String productId, int quantity, String orderId, String userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.userId = userId;
    }
    private ReserveProductCommand(Builder builder) {
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.orderId = builder.orderId;
        this.userId = builder.userId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public static class Builder {
        private String productId;
        private int quantity;
        private String orderId;
        private String userId;

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ReserveProductCommand build() {
            return new ReserveProductCommand(this);
        }
    }
}
