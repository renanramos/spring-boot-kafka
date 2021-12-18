package com.renanrramos.spring.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

	@KafkaListener(topics = "hobbit", groupId = "group_id")
	public void consumeHobbitQuotes(final ConsumerRecord<Integer, String> record) {
		log.info("Received from {}: {}",record.topic(), record.value());
	}
}