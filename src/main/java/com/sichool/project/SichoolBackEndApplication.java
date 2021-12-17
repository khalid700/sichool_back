package com.sichool.project;

import com.sichool.project.dao.ClassRoomRepository;
import com.sichool.project.dao.CourseRepository;
import com.sichool.project.dao.QuizRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.beans.BeanProperty;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SichoolBackEndApplication {

//	@Bean
//	CommandLineRunner commandLineRunner(QuizRepository quizRepository)
//	{
//		return args -> {
//			quizRepository.deleteAll().subscribe();};
//	}
	public static void main(String[] args) {
		SpringApplication.run(SichoolBackEndApplication.class, args);
	}

}
