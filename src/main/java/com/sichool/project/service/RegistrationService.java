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
        System.out.println(account);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return fetchByEmail(account.getEmail()).hasElement()
                .flatMap(is ->{
                    if (is)
                       return Mono.error(  new IllegalAccessException("email used"));
                    else
                        return accountRepository.save(account);
                });
    }

    public Mono<Account> fetchByEmail(String email)
    {
        return accountRepository.findAccountByEmail(email);
    }

    public Mono<Account> findUserByEmail(String email)
    {
        return accountRepository.findByEmail(email);
    }
}
