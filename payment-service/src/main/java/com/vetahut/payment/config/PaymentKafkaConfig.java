package com.vetahut.payment.config;

import com.vetahut.events.OrderCreatedEvent;
import com.vetahut.events.PaymentStatusEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PaymentKafkaConfig {

    @Bean
    public ConsumerFactory<String, OrderCreatedEvent> orderConsumerFactory(
            @Qualifier("baseConsumerConfigs") Map<String, Object> baseConfigs) {
        JsonDeserializer<OrderCreatedEvent> deserializer = new JsonDeserializer<>(OrderCreatedEvent.class);
        deserializer.addTrustedPackages("com.vetahut.events"); // or specific class
        Map<String, Object> consumerProps = new HashMap<>(baseConfigs);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        deserializer.setRemoveTypeHeaders(false);  // Optional for type info header

        return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), deserializer);
    }
/*    @Bean
    public ConsumerFactory<String, OrderCreatedEvent> paymentConsumerFactory(
            @Qualifier("baseConsumerConfigs") Map<String, Object> baseConfigs) {

        Map<String, Object> consumerProps = new HashMap<>(baseConfigs);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-group");

        JsonDeserializer<OrderCreatedEvent> deserializer = new JsonDeserializer<>(OrderCreatedEvent.class);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(consumerProps, new StringDeserializer(), deserializer);
    }*/

/*    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> paymentKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderCreatedEvent> paymentConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentConsumerFactory);
        return factory;
    }*/

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> orderKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderCreatedEvent> orderConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConsumerFactory(orderConsumerFactory);
        return factory;
    }

    @Bean
    public ProducerFactory<String, PaymentStatusEvent> paymentProducerFactory(
            @Qualifier("baseProducerConfigs") Map<String, Object> baseConfigs) {
        return new DefaultKafkaProducerFactory<>(baseConfigs);
    }

    @Bean
    public KafkaTemplate<String, PaymentStatusEvent> paymentKafkaTemplate(
            ProducerFactory<String, PaymentStatusEvent> paymentProducerFactory) {
        return new KafkaTemplate<>(paymentProducerFactory);
    }
}
