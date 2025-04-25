package com.vetahut.order.infrastructure.event;

import com.vetahut.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

  private final KafkaTemplate<String, OrderCreatedEvent> orderKafkaTemplate;

  public void sendOrderCreatedEvent(OrderCreatedEvent event) {
    orderKafkaTemplate.send("order-created", event);
  }
}
