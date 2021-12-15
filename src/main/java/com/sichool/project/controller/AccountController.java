package com.sichool.project.controller;

import com.sichool.project.dao.AccountRepository;
import com.sichool.project.model.Account;
import com.sichool.project.model.AuthRequest;
import com.sichool.project.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;


    @GetMapping("search/students")
    @PreAuthorize("hasAuthority('student:read')")
    public Flux<Account> searchForStudents(@Nullable @RequestParam("keyWord") String keyWord)
    {
        return accountService.searchForStudents(keyWord);
    }

    @GetMapping("search/proofs")
    @PreAuthorize("hasAuthority('prof:read')")
    public Flux<Account> searchForProofs(@Nullable @RequestParam("keyWord") String keyWord)
    {
        return accountService.searchForProofs(keyWord);
    }

    @GetMapping("fetch/doctors")
    public Flux<Account> getAllDoctors(@Nullable @RequestParam("keyWord") String keyWord)
    {
        return accountService.findAllDoctors(keyWord);
    }
    @GetMapping("fetch/my/data")
    public Mono<Account> fetchMyData(Authentication authentication)
    {
        var id = (String) authentication.getPrincipal();
        return accountService.findAccountById(id);
    }
    @GetMapping("fetch/by/id")
    public Mono<Account> findById(@PathVariable("id") String id)
    {
        return accountService.findAccountById(id);
    }


}
