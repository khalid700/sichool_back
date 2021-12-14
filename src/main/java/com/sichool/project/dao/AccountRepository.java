package com.sichool.project.dao;

import com.sichool.project.model.Account;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.io.Flushable;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findAccountByEmail(String email);
    Mono<UserDetails> findByEmail(String email);
}
