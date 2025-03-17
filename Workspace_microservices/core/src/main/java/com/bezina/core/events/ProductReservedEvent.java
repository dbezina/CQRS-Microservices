package com.bezina.core.events;

import com.bezina.core.commands.ReserveProductCommand;

public class ProductReservedEvent {
    public final String productId;
    public final int quantity;
    public final String orderId;
    public final String userId;

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

    public ProductReservedEvent(String productId, int quantity, String orderId, String userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.userId = userId;
    }
    public ProductReservedEvent(Builder builder) {
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.orderId = builder.orderId;
        this.userId = builder.userId;
    }

    public static class Builder{
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
        public ProductReservedEvent build(){
            return new ProductReservedEvent(this);
        }

    }
}
