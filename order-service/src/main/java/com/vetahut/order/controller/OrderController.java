package com.vetahut.order.controller;

import com.vetahut.order.model.OrderRequest;
import com.vetahut.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "APIs for placing orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Place a new order")
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.ok("Order placed successfully!");
    }
}
