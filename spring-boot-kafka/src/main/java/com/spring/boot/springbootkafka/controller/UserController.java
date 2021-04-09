/**
 * 
 */
package com.spring.boot.springbootkafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.springbootkafka.constants.Constants;
import com.spring.boot.springbootkafka.model.User;

/**
 * @author mayankjain
 *
 */

@RestController
@RequestMapping(value = Constants.BASE_URL)
public class UserController {
	
	@Value("${custom.kafka.topic.name}")
	private String kafka_topic;
	
	private final KafkaTemplate<String, User> kafkaTemplate;
	
	@Autowired
	public UserController(KafkaTemplate<String, User> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@GetMapping(path = "/publish/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getMessage(@PathVariable final String name) {
		
		User user = User.builder().name(name).dept("IT").salary(20000L).build();
		kafkaTemplate.send(kafka_topic, user);
		return "Published!";
	}
	
}
