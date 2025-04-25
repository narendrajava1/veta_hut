package com.vetahut.order.model;

import lombok.Data;

@Data
public class OrderRequest {
    private String customerId;
    private double amount;
}
