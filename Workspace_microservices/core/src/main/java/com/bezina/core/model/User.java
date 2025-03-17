package com.bezina.core.model;

public class User {
    private final String firstName;
    private final String lastName;
    private final String userId;
    private final PaymentDetails paymentDetails;

    public User(String firstName, String lastName, String userId, PaymentDetails paymentDetails) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.paymentDetails = paymentDetails;
    }
    public User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userId = builder.userId;
        this.paymentDetails = builder.paymentDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserId() {
        return userId;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }
    public static class Builder{
        private  String firstName;
        private  String lastName;
        private  String userId;
        private  PaymentDetails paymentDetails;

        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }
        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }
        public Builder userId(String userId){
            this.userId = userId;
            return this;
        }
        public Builder paymentDetails(PaymentDetails paymentDetails){
            this.paymentDetails = paymentDetails;
            return this;
        }
        public User build(){
            return new User(this);
        }

    }
}
