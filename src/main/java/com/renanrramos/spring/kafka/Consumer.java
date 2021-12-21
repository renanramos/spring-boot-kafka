package com.renanrramos.spring.kafka;

import io.confluent.developer.avro.Hobbit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

	@KafkaListener(topics = "hobbit-avro", groupId = "group_id")
	public void consumeHobbitQuotes(final ConsumerRecord<Integer, Hobbit> record) {
		log.info("Received from topic: {} | word: '{}' and value: {}", record.topic(), record.key(), record.value());
	}
}