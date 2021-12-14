package com.sichool.project.service;

import com.sichool.project.dao.AccountRepository;
import com.sichool.project.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<Account> registerUser(Account account)
    {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return fetchByEmail(account.getEmail())
                .switchIfEmpty(Mono.just(account))
                .flatMap(accountRepository::save);
    }

    public Mono<Account> fetchByEmail(String email)
    {
        return accountRepository.findAccountByEmail(email);
    }

    public Mono<UserDetails> findUserByEmail(String email)
    {
        return accountRepository.findByEmail(email);
    }
}
