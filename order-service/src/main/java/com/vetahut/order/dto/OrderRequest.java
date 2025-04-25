package com.vetahut.order.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderRequest {
  private String customerId;
  private double amount;
}
