package com.dbm.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class KafkaAndMongoDbApplication {
	public static void main(String[] args) {
		SpringApplication.run(KafkaAndMongoDbApplication.class, args);
	}
}
