package com.vetahut.order.model;

import com.vetahut.order.status.OrderStatus;
import java.util.UUID;
import lombok.Data;

@Data
public class Order {
  private String orderId;
  private OrderStatus status;
  private String customerId;
  private double amount;

  public Order(String customerId, double amount) {
    this.orderId = UUID.randomUUID().toString();
    this.customerId = customerId;
    this.amount = amount;
    this.status = OrderStatus.CREATED;
  }

  public void markPaid() {
    this.status = OrderStatus.PAID;
  }
}
