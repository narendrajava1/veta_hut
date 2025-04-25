package com.vetahut.factory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class PaymentProcessorFactory {
    static final Map<String,PaymentProcessor> paymentProcessorMap=new HashMap<>();

    public PaymentProcessorFactory(List<PaymentProcessor> paymentProcessorList) {
        for (PaymentProcessor paymentProcessor:paymentProcessorList){
        paymentProcessorMap.put(paymentProcessor.handleFor(),paymentProcessor);
        }
    }

    public static PaymentProcessor getProcessor(String method) {
        return Optional.ofNullable(paymentProcessorMap.get(method)).orElseThrow(()-> new IllegalArgumentException("Unknown method: " + method));
        }
    }