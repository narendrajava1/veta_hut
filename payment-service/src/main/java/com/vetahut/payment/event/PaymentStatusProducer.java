package com.vetahut.payment.event;

import com.vetahut.events.PaymentStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStatusProducer {

  private final KafkaTemplate<String, PaymentStatusEvent> paymentKafkaTemplate;

  public void sendPaymentStatus(PaymentStatusEvent event) {
    paymentKafkaTemplate.send("payment-status", event);
  }
}
