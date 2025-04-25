package com.vetahut.order.application;

import com.vetahut.events.OrderCreatedEvent;
import com.vetahut.order.dto.OrderRequest;
import com.vetahut.order.event.OrderEventProducer;
import com.vetahut.order.model.Order;
import com.vetahut.order.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderHandler {

    private final OrderDomainService domainService;
    private final OrderEventProducer producer;

    public void handle(OrderRequest request) {
        Order order = domainService.createOrder(request.getCustomerId(), request.getAmount());
        OrderCreatedEvent event = new OrderCreatedEvent(order.getOrderId(), request.getCustomerId(), "CARD",request.getAmount());
        producer.sendOrderCreatedEvent(event);
    }
}
