package com.ciandt.techlab.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ciandt.techlab.kafka.common.Constants;
import io.confluent.developer.avro.Hobbit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HobbitConsumer extends Consumer<Integer, Hobbit> {

	public HobbitConsumer(final SimpMessagingTemplate webSocket) {
		super(webSocket);
	}

	@Override
	@KafkaListener(topics = Constants.HOBBIT_TOPIC, groupId = "group_hobbit")
	protected void consumer(ConsumerRecord<Integer, Hobbit> record) {
		log.info("Received from topic: {} | word: '{}' and value: {}", record.topic(), record.key(), record.value());
		execute(record, Constants.HOBBIT_TOPIC);
	}

}