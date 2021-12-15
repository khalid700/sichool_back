package com.sichool.project.dao;

import com.sichool.project.model.Quiz;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface QuizRepository extends ReactiveMongoRepository<Quiz, String> {

    Flux<Quiz>  findAllByCourseId(String courseId);


}
