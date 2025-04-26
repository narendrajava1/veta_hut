package com.vetahut.order.model;

import com.vetahut.order.status.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.Data;

@Data
public class Order {
  private String orderId;
  private OrderStatus status;
  private String customerId;
  private double amount;

  @Enumerated(EnumType.STRING)
  private OrderType orderType;

  public Order(String customerId, double amount, String orderType) {
    this.orderId = UUID.randomUUID().toString();
    this.customerId = customerId;
    this.amount = amount;
    this.status = OrderStatus.CREATED;
    try {
      this.orderType = OrderType.valueOf(orderType);
    } catch (IllegalArgumentException e) {
      this.orderType = OrderType.DINE_IN; // or some default value
    }
  }

  public void markPaid() {
    this.status = OrderStatus.PAID;
  }
}
