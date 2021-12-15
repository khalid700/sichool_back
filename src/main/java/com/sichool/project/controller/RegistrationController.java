package com.sichool.project.controller;

import com.sichool.project.config.JWTUtil;
import com.sichool.project.model.*;
import com.sichool.project.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private JWTUtil jwtUtil;
    private PBKDF2Encoder passwordEncoder;


    @PostMapping("register")
    public Mono<Account> register(@RequestBody Account account)
    {
        return registrationService.registerUser(account);
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<Token>> login(@RequestBody AuthRequest ar) {
        return registrationService.findUserByEmail(ar.getEmail())
                .filter(userDetails -> passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new Token(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }


}
