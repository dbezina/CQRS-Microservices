package com.bezina.core.model;

public class PaymentDetails {
    private final String name;
    private final String cardNumber;
    private final int validUntilMonth;
    private final int validUntilYear;
    private final String cvv;

    public PaymentDetails(String name, String cardNumber, int validUntilMonth, int validUntilYear, String cvv) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.validUntilMonth = validUntilMonth;
        this.validUntilYear = validUntilYear;
        this.cvv = cvv;
    }
    public PaymentDetails(Builder builder) {
        this.name = builder.name;
        this.cardNumber = builder.cardNumber;
        this.validUntilMonth = builder.validUntilMonth;
        this.validUntilYear = builder.validUntilYear;
        this.cvv = builder.cvv;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getValidUntilMonth() {
        return validUntilMonth;
    }

    public int getValidUntilYear() {
        return validUntilYear;
    }

    public String getCvv() {
        return cvv;
    }

    public static class Builder{
        private  String name;
        private  String cardNumber;
        private  int validUntilMonth;
        private  int validUntilYear;
        private  String cvv;
        public Builder name(String name){
            this.name = name;
            return this;
        }
        public Builder cardNumber(String cardNumber){
            this.cardNumber = cardNumber;
            return this;
        }
        public Builder validUntilMonth(int validUntilMonth){
            this.validUntilMonth = validUntilMonth;
            return this;
        }
        public Builder validUntilYear(int validUntilYear){
            this.validUntilYear = validUntilYear;
            return this;
        }
        public Builder cvv(String cvv){
            this.cvv = cvv;
            return this;
        }

        public PaymentDetails build(){
            return new PaymentDetails(this);
        }
    }
}
