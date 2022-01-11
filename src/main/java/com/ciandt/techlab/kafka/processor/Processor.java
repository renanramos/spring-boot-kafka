package com.ciandt.techlab.kafka.processor;

import com.ciandt.techlab.kafka.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class Processor {

	@Autowired
	public void process(final StreamsBuilder streamsBuilder) {

		final Serde<Integer> integerSerde = Serdes.Integer();
		final Serde<String> stringSerde = Serdes.String();
		final Serde<Long> longSerde = Serdes.Long();

		final KStream<Integer, String> textLines = streamsBuilder
						.stream(Constants.HOBBIT_TOPIC, Consumed.with(integerSerde, stringSerde));

		final KTable<String, Long> wordCounts = textLines
						.flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
						.groupBy((key, value) -> value, Grouped.with(stringSerde, stringSerde))
						.count(Materialized.as(Constants.COUNT));

		wordCounts.toStream().to("streams-wordcount-output", Produced.with(stringSerde, longSerde));
	}

}