package com.vetahut.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
  private String orderId;
  private String customerId;
  private String paymentMethod;
  private String orderType;
  private double amount;
}
