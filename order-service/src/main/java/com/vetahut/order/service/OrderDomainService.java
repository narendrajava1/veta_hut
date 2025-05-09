package com.vetahut.order.service;

import com.vetahut.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderDomainService {
  public Order createOrder(String customerId, double amount, String orderType) {
    return new Order(customerId, amount, orderType);
  }
}
