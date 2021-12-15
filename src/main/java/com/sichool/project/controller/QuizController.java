package com.sichool.project.controller;

import com.sichool.project.dao.QuizRepository;
import com.sichool.project.model.Account;
import com.sichool.project.model.Quiz;
import com.sichool.project.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("quiz")
@AllArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("create")
    public Mono<Quiz> create(Authentication authentication, @RequestBody Quiz quiz)
    {
        var account = (Account) authentication.getCredentials();
        return quizService.create(account, quiz);
    }

    @GetMapping("fetch/by/id/{id}")
    public Mono<Quiz> findById(@PathVariable("id") String id)
    {
        return quizService.getQuizById(id);
    }

    @GetMapping("fetch/by/courseId/{courseId}")
    public Flux<Quiz> findAllByCourseId(@PathVariable("courseId") String courseId)
    {
        return quizService.findAllByCourseId(courseId);
    }




}
