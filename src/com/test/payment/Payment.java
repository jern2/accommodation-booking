package com.test.payment;

public class Payment {
    private String type;
    private String details;
    private double amount;

    public Payment(String type, String details, double amount) {
        this.type = type;
        this.details = details;
        this.amount = amount;
    }
    

    @Override
    public String toString() {
        return "Payment{" +
                "type='" + type + '\'' +
                ", details='" + details + '\'' +
                ", amount=" + amount +
                '}';
    }
}

