package com.bezina.core.events;

import com.bezina.core.commands.ProcessPaymentCommand;
import com.bezina.core.model.PaymentDetails;

public class PaymentProcessedEvent {
    private final String paymentId;
    private final String orderId;
    private final PaymentDetails paymentDetails;

    public static PaymentProcessedEvent from(ProcessPaymentCommand processPaymentCommand) {
        return new PaymentProcessedEvent(
                processPaymentCommand.getPaymentId(),
                processPaymentCommand.getOrderId(),
                processPaymentCommand.getPaymentDetails()
        );
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getOrderId() {
        return orderId;
    }
    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public PaymentProcessedEvent(String paymentId, String orderId, PaymentDetails paymentDetails) {

        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentDetails = paymentDetails;
    }

    public PaymentProcessedEvent(Builder builder) {
        this.paymentId = builder.paymentId;
        this.orderId = builder.orderId;
        this.paymentDetails = builder.paymentDetails;
    }

    public static class Builder{
        private String paymentId;
        private String orderId;
        private PaymentDetails paymentDetails;
        public Builder paymentId(String paymentId){
            this.paymentId = paymentId;
            return this;
        }
        public Builder orderId(String orderId){
            this.orderId = orderId;
            return this;
        }
        public Builder paymentDetails(PaymentDetails paymentDetails){
            this.paymentDetails = paymentDetails;
            return this;
        }
        public PaymentProcessedEvent build(){
            return new PaymentProcessedEvent(this);
        }
    }
}
