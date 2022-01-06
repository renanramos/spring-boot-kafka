package com.ciandt.techlab.kafka;

import com.ciandt.techlab.kafka.common.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@EnableKafkaStreams
public class SpringKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
	}

	@Bean
	NewTopic friendsAvro() {
		return TopicBuilder.name(Constants.FRIENDS_TOPIC).partitions(3).replicas(2).build();
	}

	@Bean
	NewTopic hobbitAvro() {
		return TopicBuilder.name(Constants.HOBBIT_TOPIC).partitions(3).replicas(2).build();
	}

}