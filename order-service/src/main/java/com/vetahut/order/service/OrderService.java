package com.vetahut.order.service;

import com.vetahut.events.OrderCreatedEvent;
import com.vetahut.events.PaymentStatusEvent;
import com.vetahut.order.event.OrderEventProducer;
import com.vetahut.order.model.Order;
import com.vetahut.order.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderEventProducer producer;

    // Simulating a simple in-memory order store
    private final Map<String, OrderStatus> orderStore = new ConcurrentHashMap<>();



    @KafkaListener(topics = "payment-status", groupId = "order-group")
    public void handlePaymentStatus(PaymentStatusEvent event, Acknowledgment ack) {
        System.out.println("Received payment status for order " + event.getOrderId() + ": " + event.getStatus());
        // update order status logic here (e.g., DB update)

        if ("PAID".equalsIgnoreCase(event.getStatus())) {
            orderStore.put(event.getOrderId(), OrderStatus.PAID);
        } else if ("FAILED".equalsIgnoreCase(event.getStatus())) {
            orderStore.put(event.getOrderId(), OrderStatus.FAILED);
            System.out.println("Compensating transaction triggered: Cancelling order " + event.getOrderId());
            // Compensation logic (e.g., refund or notification)
        }
        ack.acknowledge(); // manually commit offset
    }
}
