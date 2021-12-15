package com.sichool.project.controller;

import com.sichool.project.model.Account;
import com.sichool.project.model.Notification;
import com.sichool.project.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("notification")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("")
    public Flux<Notification> findMyNotification(Authentication authentication)
    {
        return notificationService.getMyNotification(authentication);
    }
}
