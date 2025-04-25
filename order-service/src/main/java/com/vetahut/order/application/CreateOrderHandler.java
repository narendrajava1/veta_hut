package com.vetahut.order.application;

import com.vetahut.events.OrderCreatedEvent;
import com.vetahut.order.dto.OrderRequest;
import com.vetahut.order.infrastructure.event.OrderEventProducer;
import com.vetahut.order.infrastructure.fallback.RetryQueue;
import com.vetahut.order.model.Order;
import com.vetahut.order.service.OrderDomainService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderHandler {

    private final OrderDomainService domainService;
    private final OrderEventProducer producer;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryQueue retryQueue;


    public void handle(OrderRequest request) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("orderServiceBreaker");
        Runnable task = () -> {
            Order order = domainService.createOrder(request.getCustomerId(), request.getAmount());
            OrderCreatedEvent event = new OrderCreatedEvent(order.getOrderId(), request.getCustomerId(), "CARD",request.getAmount());
            producer.sendOrderCreatedEvent(event);
        };

        Runnable decoratedTask = CircuitBreaker.decorateRunnable(circuitBreaker, task);

        try {
            decoratedTask.run();
        } catch (Exception e) {
            // Fallback logic can include retrying after delay, queuing the request, or manual intervention
            System.err.println("Circuit breaker triggered: " + e.getMessage());
            // Example: save to retry table or send to dead letter queue
            fallback(request,e);
        }

    }

    private void fallback(OrderRequest request, Exception e) {
        // Log fallback
        System.err.println("Fallback triggered for customerId=" + request.getCustomerId() + " due to: " + e.getMessage());

        // Example fallback behavior:
        // 1. Save to retry queue
        retryQueue.enqueue(request);
        // 2. Persist to DB for retry
        // 3. Notify via alert or metrics
    }
}
