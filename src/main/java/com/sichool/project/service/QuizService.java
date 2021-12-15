package com.sichool.project.service;

import com.sichool.project.dao.QuizRepository;
import com.sichool.project.model.Account;
import com.sichool.project.model.Notification;
import com.sichool.project.model.Quiz;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final CourseService courseService;
    private final NotificationService notificationService;
    public Mono<Quiz> create(Account account , Quiz quiz)
    {
        quiz.setProofId(account.getId());
        return quizRepository.save(quiz)
                .doOnSuccess(q->{
                    courseService.getStudentsIds(q.getCourseId())
                            .map(list-> Notification.builder()
                                    .owner(account)
                                    .destinationIds(list)
                                    .status(true)
                                    .type("quiz")
                                    .viewedBy(new HashSet<>())
                                    .description("new quiz available")
                                    .build())
                            .flatMap(notificationService::create)
                            .subscribe();
                });
    }


    public Mono<Quiz> getQuizById(String id)
    {
        return quizRepository.findById(id);
    }

    public Flux<Quiz> findAllByCourseId(String courseID)
    {
        return quizRepository.findAllByCourseId(courseID);
    }

}
