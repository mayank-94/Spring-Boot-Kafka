package com.spring.boot.springbootkafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.spring.boot.springbootkafka.model.User;

@Service
public class KafkaConsumer {
	
	@KafkaListener(topics = "kafka_json", groupId = "json_group", 
			containerFactory = "userKafkaListenerContainerfactory")
	public void consumeJson(User user) {
		System.out.println("Message Consumed in Json: "+user);
	}
}
