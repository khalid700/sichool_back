package com.sichool.project.config;

import com.sichool.project.model.Authority;
import com.sichool.project.service.AccountService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private JWTUtil jwtUtil;
    private AccountService accountService;

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.getUsernameFromToken(authToken);
        return Mono.just(jwtUtil.validateToken(authToken))
                .filter(valid -> valid)
                .switchIfEmpty(Mono.empty())
                .flatMap(valid -> injectAccount(username, authToken));
    }

    public Mono<Authentication> injectAccount(String id, String authToken)
    {
        return accountService.findAccountById(id)
                .map(a->{
                    Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
                    List<Map<String, String>> authorities = ((List<Map<String, String>>) claims.get("role"));

                    Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                            .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                            .collect(Collectors.toSet());
                    return new UsernamePasswordAuthenticationToken(
                            id,
                            a,
                            simpleGrantedAuthorities//role,

                    );
                });

    }

}