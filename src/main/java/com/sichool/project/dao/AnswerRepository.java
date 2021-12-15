package com.sichool.project.dao;

import com.sichool.project.model.Answer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface AnswerRepository  extends ReactiveMongoRepository<Answer, String> {
    Flux<Answer> findAllByQuizId(String quizId);
}
