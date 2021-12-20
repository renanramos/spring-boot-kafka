package com.renanrramos.spring.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Processor {

	@Autowired
	public void process(final StreamsBuilder streamsBuilder) {

		final Serde<Integer> integerSerde = Serdes.Integer();
		final Serde<String> stringSerde = Serdes.String();
		final Serde<Long> longSerde = Serdes.Long();

		final KStream<Integer, String> textLines = streamsBuilder
						.stream("hobbit", Consumed.with(integerSerde, stringSerde));

		final KTable<String, Long> wordCounts = textLines
						.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
						.groupBy((key, value) -> value, Grouped.with(stringSerde, stringSerde))
						.count();

		wordCounts.toStream().to("streams-wordcount-output", Produced.with(stringSerde, longSerde));
	}

}