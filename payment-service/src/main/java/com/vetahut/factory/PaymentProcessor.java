package com.vetahut.factory;

public interface PaymentProcessor {
    void process(String orderId, double amount);
    String handleFor();
}
