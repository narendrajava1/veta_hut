package com.vetahut.order.application;

import com.vetahut.order.dto.OrderRequest;
import com.vetahut.order.infrastructure.fallback.RetryQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetryProcessor {

  private final CreateOrderHandler createOrderHandler;
  private final RetryQueue retryQueue;

  // Runs every 30 seconds to retry failed requests
  @Scheduled(fixedRate = 30000)
  public void processRetries() {
    while (!retryQueue.isEmpty()) {
      OrderRequest request = retryQueue.dequeue();
      if (request != null) {
        try {
          log.info("Retrying order for customerId={}", request.getCustomerId());
          createOrderHandler.handle(request);
        } catch (Exception e) {
          log.error(
              "Retry failed again for customerId={} - re-enqueueing", request.getCustomerId(), e);
          retryQueue.enqueue(request);
        }
      }
    }
  }
}
