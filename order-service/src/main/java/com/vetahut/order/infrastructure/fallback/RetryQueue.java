package com.vetahut.order.infrastructure.fallback;

import com.vetahut.order.dto.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component
public class RetryQueue {

    private final Queue<OrderRequest> retryQueue = new ConcurrentLinkedQueue<>();

    public void enqueue(OrderRequest request) {
        retryQueue.add(request);
        log.warn("Request enqueued for retry: {}", request);
    }

    public OrderRequest dequeue() {
        return retryQueue.poll();
    }

    public boolean isEmpty() {
        return retryQueue.isEmpty();
    }
}