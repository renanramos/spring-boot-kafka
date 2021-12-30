package com.renanrramos.spring.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.renanrramos.spring.kafka.common.Constants;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@JsonSerialize
@AllArgsConstructor
public abstract class Consumer<K, V> {

	private static final ObjectMapper json = new ObjectMapper();
	private final SimpMessagingTemplate webSocket;

	private static final String BASE_TOPIC = Constants.SIMPLE_BROKER + "/";

	public void execute(final ConsumerRecord<K, V> record, final String topic) throws JsonProcessingException {
		final String value = json.writeValueAsString(record.value());
		this.webSocket.convertAndSend(BASE_TOPIC + topic, value);
	}

}