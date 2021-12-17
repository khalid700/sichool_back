package com.sichool.project.dao;

import com.sichool.project.model.Account;
import com.sichool.project.model.UserRoles;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface AccountRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findAccountByEmail(String email);
    Mono<Account> findByEmail(String email);
    Flux<Account> findAllByRoleAndFullNameContainingOrRoleAndEmailIsContaining(UserRoles role, String fullName, UserRoles role2, String email);
    Flux<Account> findAllByRole(UserRoles role);
    Flux<Account> findAllByIdIn(Set<String> ids);
}
