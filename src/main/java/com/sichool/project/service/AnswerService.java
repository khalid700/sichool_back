package com.sichool.project.service;


import com.sichool.project.dao.AnswerRepository;
import com.sichool.project.dao.QuizRepository;
import com.sichool.project.model.Account;
import com.sichool.project.model.Answer;
import com.sichool.project.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AnswerService {
    private AccountService accountService;
    private AnswerRepository answerRepository;
    private QuizRepository quizRepository;
    private NotificationService notificationService;

    public Mono<Answer> create(Account account , Answer answer)
    {
        answer.setStudentId(account.getId());
        return answerRepository.save(answer)
                .doOnSuccess(answer1 ->{
                    quizRepository.findById(answer1.getQuizId())
                            .map(q->
                                Notification.builder()
                                        .owner(account)
                                        .destinationIds(Set.of(q.getProofId()))
                                        .status(true)
                                        .type("answer")
                                        .viewedBy(new HashSet<>())
                                        .description(String.format("new answer to quiz  %s", q.getName()))
                                        .build()
                            ).flatMap(notificationService::create);
                });

    }

    public Mono<Answer> getById(String id)
    {
        return answerRepository.findById(id);
    }

    public Flux<Answer> findByQuizId(String quizId)
    {
        return answerRepository.findAllByQuizId(quizId);
    }




}
