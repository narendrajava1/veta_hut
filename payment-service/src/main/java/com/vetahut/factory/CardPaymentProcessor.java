package com.vetahut.factory;

import org.springframework.stereotype.Component;

@Component
public class CardPaymentProcessor implements PaymentProcessor {
  public void process(String orderId, double amount) {
    System.out.println("Processing card payment for order " + orderId);
  }

  @Override
  public String handleFor() {
    return "CARD";
  }
}
