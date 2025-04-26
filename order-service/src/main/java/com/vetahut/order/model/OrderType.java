package com.vetahut.order.model;

import lombok.Getter;

@Getter
public enum OrderType {
  DINE_IN("DINE_IN", "Customer eats at restaurant"),
  TAKEAWAY("TAKEAWAY", "Customer picks up & leaves"),
  DELIVERY("DELIVERY", "Delivery to customer address");

  private final String description;
  private final String orderType;

  OrderType(String orderType, String description) {
    this.description = description;
    this.orderType = orderType;
  }
}
