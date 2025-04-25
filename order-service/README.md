✅ Your `order-service` module is now fully scaffolded with:

- **Swagger-enabled REST API** at `/api/orders`
- **Spring Kafka producer** that sends `OrderCreatedEvent` to the `order-created` topic
- **Integrated with `common-events` module**

You can now build and run it using:
```bash
./mvnw spring-boot:run -pl order-service
```

Would you like:
- Consumer implementation in `payment-service`?
- Docker Compose setup to run Kafka + Zookeeper locally?
- Full `common-events` code with shared event classes?

Let’s keep building!

order-service/
├── pom.xml
└── src/main/java/com/pizzahut/order/
├── controller/OrderController.java
├── service/OrderService.java
├── event/OrderEventProducer.java
├── model/OrderRequest.java
└── config/KafkaProducerConfig.java


## To format the code run this below command
``mvn com.coveo:fmt-maven-plugin:2.9.1:format``