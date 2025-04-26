package com.vetahut.order.infrastructure.config;

import com.vetahut.events.OrderCreatedEvent;
import com.vetahut.events.PaymentStatusEvent;
import com.vetahut.order.model.OrderType;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class OrderKafkaConfig {

  @Bean
  public ConsumerFactory<String, PaymentStatusEvent> paymentConsumerFactory(
      @Qualifier("baseConsumerConfigs") Map<String, Object> baseConfigs) {

    Map<String, Object> consumerProps = new HashMap<>(baseConfigs);
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "order-group");

    JsonDeserializer<PaymentStatusEvent> deserializer =
        new JsonDeserializer<>(PaymentStatusEvent.class);
    deserializer.addTrustedPackages("*");
    deserializer.setRemoveTypeHeaders(false);
    return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, PaymentStatusEvent>
      paymentKafkaListenerContainerFactory(
          ConsumerFactory<String, PaymentStatusEvent> paymentConsumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, PaymentStatusEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(paymentConsumerFactory);
    factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
    return factory;
  }

  @Bean
  public ProducerFactory<String, OrderCreatedEvent> orderProducerFactory(
      @Qualifier("baseProducerConfigs") Map<String, Object> baseConfigs) {
    baseConfigs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, KafkaGeographicPartitioner.class);
    baseConfigs.put("partition.booking.order_type", OrderType.DELIVERY.getOrderType());
    return new DefaultKafkaProducerFactory<>(baseConfigs);
  }

  @Bean
  public KafkaTemplate<String, OrderCreatedEvent> orderKafkaTemplate(
      ProducerFactory<String, OrderCreatedEvent> orderProducerFactory) {
    return new KafkaTemplate<>(orderProducerFactory);
  }
}
