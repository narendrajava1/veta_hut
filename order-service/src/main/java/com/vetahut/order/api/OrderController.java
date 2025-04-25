package com.vetahut.order.api;

import com.vetahut.order.application.CreateOrderHandler;
import com.vetahut.order.dto.OrderRequest;
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
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "APIs for placing orders")
public class OrderController {

  private final OrderService orderService;

  private final CreateOrderHandler handler;

  @Operation(summary = "Place a new order")
  @PostMapping
  public ResponseEntity<String> create(@RequestBody OrderRequest request) {
    handler.handle(request);
    return ResponseEntity.ok("Order created");
  }
}
