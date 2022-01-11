package com.ciandt.techlab.kafka.consumer;

import com.ciandt.techlab.kafka.common.Constants;
import io.confluent.developer.avro.Friends;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FriendsConsumer extends Consumer<Integer, Friends>{

	public FriendsConsumer(final SimpMessagingTemplate webSocket) {
		super(webSocket);
	}

	@Override
	@KafkaListener(topics = Constants.FRIENDS_TOPIC, groupId = "group_friends")
	protected void consumer(ConsumerRecord<Integer, Friends> record) {
		log.info("Received from topic: {} | word: '{}' and value: {}", record.topic(), record.key(), record.value());
		execute(record, Constants.FRIENDS_TOPIC);
	}

}