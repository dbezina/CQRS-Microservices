package com.bezina.core.commands;

import com.bezina.core.model.PaymentDetails;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CancelProductReservationCommand {
    @TargetAggregateIdentifier
    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;
    private final String reason;

    public CancelProductReservationCommand(String productId, int quantity, String orderId, String userId, String reason) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.userId = userId;
        this.reason = reason;
    }
    public CancelProductReservationCommand(Builder builder) {
        this.productId = builder.productId;
        this.quantity = builder.quantity;
        this.orderId = builder.orderId;
        this.userId = builder.userId;
        this.reason = builder.reason;
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

    public String getReason() {
        return reason;
    }

    public static class Builder{
        private  String productId;
        private  int quantity;
        private  String orderId;
        private  String userId;
        private  String reason;

        public Builder productId(String productId){
            this.productId = productId;
            return this;
        }
        public Builder orderId(String orderId){
            this.orderId = orderId;
            return this;
        }
        public Builder userId(String UserId){
            this.userId = userId;
            return this;
        }
        public Builder reason(String reason){
            this.reason = reason;
            return this;
        }
        public Builder quantity(int quantity){
            this.quantity = quantity;
            return this;
        }
        public CancelProductReservationCommand build (){
            return new CancelProductReservationCommand(this);
        }

    }
}
