// Root folder structure for saga-based microservices using Spring Boot & Kafka

```
saga-choreography-springboot/
├── common-events/                  # Shared module for event models
│   ├── pom.xml
│   └── src/main/java/com/pizzahut/events/
│       ├── OrderCreatedEvent.java
│       ├── PaymentCompletedEvent.java
│       └── InventoryReservedEvent.java
├── order-service/
│   ├── pom.xml
│   └── src/main/java/com/pizzahut/order/
│       ├── controller/OrderController.java
│       ├── service/OrderService.java
│       ├── event/OrderEventProducer.java
│       ├── model/OrderRequest.java
│       └── config/KafkaProducerConfig.java
├── payment-service/
│   ├── pom.xml
│   └── src/main/java/com/pizzahut/payment/
│       ├── listener/OrderCreatedListener.java
│       ├── event/PaymentEventProducer.java
│       └── config/KafkaConfig.java
├── inventory-service/
│   ├── pom.xml
│   └── src/main/java/com/pizzahut/inventory/
│       ├── listener/PaymentCompletedListener.java
│       ├── event/InventoryEventProducer.java
│       └── config/KafkaConfig.java
├── notification-service/
│   ├── pom.xml
│   └── src/main/java/com/pizzahut/notification/
│       ├── listener/InventoryReservedListener.java
│       └── config/KafkaConfig.java
└── docker-compose.yml              # Optional: To run Kafka + Zookeeper locally 
```
