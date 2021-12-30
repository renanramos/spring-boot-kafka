package com.renanrramos.spring.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.renanrramos.spring.kafka.common.Constants;
import io.confluent.developer.avro.Friends;
import io.confluent.developer.avro.Hobbit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@JsonSerialize
public class HobbitConsumer {

	private static final ObjectMapper json = new ObjectMapper();

	@Autowired
	private SimpMessagingTemplate webSocket;

	@KafkaListener(topics = Constants.HOBBIT_TOPIC, groupId = "group_hobbit")
	public void consumeHobbitQuotes(final ConsumerRecord<Integer, Hobbit> record) {
		log.info("Received from topic: {} | word: '{}' and value: {}", record.topic(), record.key(), record.value());
		try {
			final String s = json.writeValueAsString(record.value());
			this.webSocket.convertAndSend(Constants.SIMPLE_BROKER + "/" + Constants.HOBBIT_TOPIC, s);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}
}