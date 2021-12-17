package com.sichool.project.service;

import com.sichool.project.dao.AccountRepository;
import com.sichool.project.model.Account;
import com.sichool.project.model.ClassRoom;
import com.sichool.project.model.UserRoles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;


@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Flux<Account> searchForStudents(String keyWord)
    {
        if (keyWord ==null || keyWord.isEmpty() || keyWord.isBlank())
            return accountRepository.findAllByRole(UserRoles.STUDENT)
                    .map(this::makePassNull);
            return accountRepository
                    .findAllByRoleAndFullNameContainingOrRoleAndEmailIsContaining(UserRoles.STUDENT, keyWord,UserRoles.STUDENT, keyWord )
                    .map(this::makePassNull);
    }

    public Flux<Account> searchForProofs(String keyWord)
    {
        if (keyWord ==null || keyWord.isEmpty() || keyWord.isBlank())
            return accountRepository.findAllByRole(UserRoles.PROF)
                    .map(this::makePassNull);
        return accountRepository
                .findAllByRoleAndFullNameContainingOrRoleAndEmailIsContaining(UserRoles.PROF, keyWord, UserRoles.PROF, keyWord)
                .map(this::makePassNull);
    }

    public Flux<Account> findAllDoctors(String keyWord)
    {
        if (keyWord ==null || keyWord.isEmpty() || keyWord.isBlank())
            return accountRepository.findAllByRole(UserRoles.DOCTOR)
                    .map(this::makePassNull);
        return accountRepository
                .findAllByRoleAndFullNameContainingOrRoleAndEmailIsContaining(UserRoles.DOCTOR,keyWord,UserRoles.DOCTOR,keyWord)
                .map(this::makePassNull);
    }

    private  Account makePassNull(Account account) {
        account.setPassword(null);
        return account;
    }
    public Mono<Account> findAccountById(String id)
    {
        return accountRepository
                .findById(id)
                .map(this::makePassNull);
    }

    public Flux<Account> findAccountsIn(Set<String> ids) {
        return accountRepository.findAllByIdIn(ids).map(this::makePassNull);
    }

//    public Mono<Long> countUsersByRole(UserRoles role)
//    {
//
//    }
}
