package com.ciandt.techlab.kafka.consumer;

import com.ciandt.techlab.kafka.common.Constants;
import com.ciandt.techlab.kafka.common.EntityDTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
@JsonSerialize
@AllArgsConstructor
public abstract class Consumer<K, V> {

	private final SimpMessagingTemplate webSocket;

	private static final String BASE_TOPIC = Constants.SIMPLE_BROKER + "/";

	public void execute(final ConsumerRecord<K, V> record, final String topic) {
			webSocket.convertAndSend(BASE_TOPIC + topic, EntityDTO
							.builder()
							.offset(record.offset())
							.partition(record.partition())
							.value(record.value())
							.build());
	}

	protected abstract void consumer(final ConsumerRecord<K, V> record);
}