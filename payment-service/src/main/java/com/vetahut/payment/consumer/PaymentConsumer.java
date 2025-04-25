package com.vetahut.payment.consumer;

import com.vetahut.events.OrderCreatedEvent;
import com.vetahut.events.PaymentStatusEvent;
import com.vetahut.factory.PaymentProcessor;
import com.vetahut.factory.PaymentProcessorFactory;
import com.vetahut.payment.event.PaymentStatusProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {
    private final PaymentStatusProducer producer;
/*    @KafkaListener(topics = "order-created", groupId = "payment-group")
    public void consume(OrderCreatedEvent event) {
        System.out.println("Processing payment for order: " + event);
        // add payment logic here
        // Simulate successful payment
        PaymentStatusEvent statusEvent = new PaymentStatusEvent(event.getOrderId(), "PAID");
        producer.sendPaymentStatus(statusEvent);
    }*/

    @KafkaListener(topics = "order-created", groupId = "payment-group")
    public void consume(OrderCreatedEvent event) {
        System.out.println("Processing payment for order: " + event);
        try {
            PaymentProcessor paymentProcessor = PaymentProcessorFactory.getProcessor(event.getPaymentMethod());
            paymentProcessor.process(event.getOrderId(), event.getAmount());

            // Simulate a condition for failure (e.g., amount over 100 fails)
            if (event.getAmount() > 100) {
                throw new RuntimeException("Insufficient balance");
            }

            PaymentStatusEvent statusEvent = new PaymentStatusEvent(event.getOrderId(), "PAID");
            producer.sendPaymentStatus(statusEvent);
        } catch (Exception e) {
            System.out.println("Payment failed: " + e.getMessage());
            PaymentStatusEvent statusEvent = new PaymentStatusEvent(event.getOrderId(), "FAILED");
            producer.sendPaymentStatus(statusEvent);
        }
    }

}
