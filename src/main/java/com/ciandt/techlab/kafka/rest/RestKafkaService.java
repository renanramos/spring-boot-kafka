package com.ciandt.techlab.kafka.rest;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestKafkaService {

	private final StreamsBuilderFactoryBean factoryBean;

	@GetMapping("/count/{word}")
	public Long getCount(@PathVariable final String word) {

		final KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();

		final ReadOnlyKeyValueStore<String, Long> counts =
						kafkaStreams.store(StoreQueryParameters.fromNameAndType("counts", QueryableStoreTypes.keyValueStore()));

		return counts.get(word);
	}
}