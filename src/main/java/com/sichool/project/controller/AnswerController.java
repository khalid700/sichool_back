package com.sichool.project.controller;

import com.sichool.project.model.Account;
import com.sichool.project.model.Answer;
import com.sichool.project.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("answer")
@AllArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("create")
    public Mono<Answer> create(Authentication authentication, @RequestBody Answer answer)
    {
        var account = (Account) authentication.getCredentials();
        return answerService.create(account, answer);
    }

    @GetMapping("fetch/by/id/{id}")
    public Mono<Answer> findById(@PathVariable("id") String id)
    {
        return answerService.getById(id);
    }


    @GetMapping("fetch/by/quizId/{quizId}")
    public Flux<Answer> findByQuizId(@PathVariable("quizId") String quizId)
    {
        return answerService.findByQuizId(quizId);
    }
}
