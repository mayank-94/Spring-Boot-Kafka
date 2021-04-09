package com.spring.boot.springbootkafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.spring.boot.springbootkafka.model.User;

@Configuration
@EnableKafka
public class ConfigConsumer {
	
	@Value("${custom.kafka.server}")
	private String kafka_server;
	
	@Value("${custom.kafka.group.id}")
	private String kafka_group;
	
	@Bean
	public ConsumerFactory<String, User> userConsumeractory(){
		Map<String, Object> config = new HashMap<String, Object>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_server);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, kafka_group);
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		
		return new DefaultKafkaConsumerFactory<String, User>(config, new StringDeserializer(),
				new JsonDeserializer<>(User.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerfactory(){
		ConcurrentKafkaListenerContainerFactory<String, User> factory = 
				new ConcurrentKafkaListenerContainerFactory<String, User>();
		factory.setConsumerFactory(userConsumeractory());
		return factory;
	}
}
