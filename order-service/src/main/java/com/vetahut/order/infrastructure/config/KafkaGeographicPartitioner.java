package com.vetahut.order.infrastructure.config;

import com.vetahut.events.OrderCreatedEvent;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaGeographicPartitioner implements Partitioner {

  private static Logger log = LoggerFactory.getLogger(KafkaGeographicPartitioner.class);
  private String orderType;

  String partitionKey = null;

  /**
   * Algorithm 1) Get number of partitions 2) if numberOfPartitions < 1 Hash the key & modulo with
   * number of partition else Check the GeoData for country of origin & if its USA send data to
   * partition - 0
   *
   * <p>NOTE - Partition 0 can still contains data from other countries, but the data belonging to
   * USA will always lands on Partition - 0
   */
  @Override
  public void configure(Map<String, ?> configs) {
    orderType = configs.get("partition.booking.order_type").toString();
  }

  @Override
  public int partition(
      String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

    //		 BookingKey bookingKey = (BookingKey) key;
    //		 partitionKey = bookingKey.getCustomerId();
    //		//Ignore bookingKey.getBookingDate()
    //		 keyBytes = partitionKey.getBytes();
    List partitionInfos = cluster.partitionsForTopic(topic);
    int numberOfPartitions = partitionInfos.size();
    if (numberOfPartitions > 1
        && ((OrderCreatedEvent) value).getOrderType().equalsIgnoreCase(orderType)) {
      return 0;
    } else {
      return Utils.toPositive(Utils.murmur2(keyBytes)) % numberOfPartitions;
    }
  }

  @Override
  public void close() {
    // TODO Auto-generated method stub

  }
}
