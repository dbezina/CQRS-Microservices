package com.bezina.core.commands;

import com.bezina.core.model.PaymentDetails;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ProcessPaymentCommand {
    @TargetAggregateIdentifier
    private final String paymentId;
    private final String orderId;
    private final PaymentDetails paymentDetails;
    public ProcessPaymentCommand(Builder builder) {
        this.paymentId = builder.paymentId;
        this.orderId = builder.orderId;
        this.paymentDetails = builder.paymentDetails;
    }

    public ProcessPaymentCommand(String paymentId, String orderId, PaymentDetails paymentDetails) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.paymentDetails = paymentDetails;
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

        public ProcessPaymentCommand build(){
            return new ProcessPaymentCommand(this);

        }
    }
}
