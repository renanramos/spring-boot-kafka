package com.ciandt.techlab.kafka.producer;

import com.github.javafaker.Faker;
import com.ciandt.techlab.kafka.common.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

	private final KafkaTemplate<Integer, String> kafkaTemplate;

	private final Faker faker = Faker.instance();

	@Async
	@EventListener(ApplicationStartedEvent.class)
	public void produce() {
    final Flux<Long> interval = Flux.interval(Duration.ofMillis(3_000));

		final Flux<String> friendsQuotes = Flux.fromStream(Stream.generate(() -> faker.friends().quote()));
		final Flux<String> hobbitQuotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

		Flux.zip(interval, friendsQuotes, hobbitQuotes)
						.map(it -> {
							kafkaTemplate.send(Constants.FRIENDS_TOPIC, faker.random().nextInt(42), it.getT2());
							kafkaTemplate.send(Constants.HOBBIT_TOPIC, faker.random().nextInt(42), it.getT3());
							return it;
						})
						.blockLast();
	}

}