package com.sichool.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SichoolBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(SichoolBackEndApplication.class, args);
	}

}
