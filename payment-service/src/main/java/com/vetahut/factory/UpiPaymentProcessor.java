package com.vetahut.factory;

import org.springframework.stereotype.Component;

@Component
public class UpiPaymentProcessor implements PaymentProcessor {
    public void process(String orderId, double amount) {
        System.out.println("Processing UPI payment for order " + orderId);
    }

    @Override
    public String handleFor() {
        return "UPI";
    }
}