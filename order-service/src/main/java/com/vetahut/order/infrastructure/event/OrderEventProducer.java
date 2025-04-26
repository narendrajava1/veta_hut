package com.vetahut.order.infrastructure.event;

import com.vetahut.events.OrderCreatedEvent;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {

  private final KafkaTemplate<String, OrderCreatedEvent> orderKafkaTemplate;

  public String sendOrderCreatedEvent(OrderCreatedEvent event) {
    CompletableFuture<SendResult<String, OrderCreatedEvent>> resultCompletableFuture =
        orderKafkaTemplate.send("order-created", event.getOrderType(), event);
    resultCompletableFuture.whenComplete(
        new BiConsumer<SendResult<String, OrderCreatedEvent>, Throwable>() {
          @Override
          public void accept(SendResult<String, OrderCreatedEvent> result, Throwable u) {
            if (u != null) {
              log.error("error while send ing to the topic: {}", u);
            } else {
              ProducerRecord<String, OrderCreatedEvent> record = result.getProducerRecord();
              OrderCreatedEvent data = (OrderCreatedEvent) record.value();

              log.info(
                  "Producing request succeeded data is: {} and partition is: {}, key is: {},topic is: {} at timestamp is: {}",
                  data,
                  record.partition(),
                  record.key(),
                  record.topic(),
                  record.timestamp());
            }
          }
        });
    return event.getOrderType();
  }
}
