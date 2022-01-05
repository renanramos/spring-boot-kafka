package com.ciandt.techlab.kafka.consumer;

import com.ciandt.techlab.kafka.common.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
@JsonSerialize
@AllArgsConstructor
public abstract class Consumer<K, V> {

	private final ObjectMapper json = new ObjectMapper();
	private final SimpMessagingTemplate webSocket;

	private static final String BASE_TOPIC = Constants.SIMPLE_BROKER + "/";

	public void execute(final ConsumerRecord<K, V> record, final String topic) {
		try {
			final String value = json.writeValueAsString(record.value());
			webSocket.convertAndSend(BASE_TOPIC + topic, value);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}

	protected abstract void consumer(final ConsumerRecord<K, V> record);
}