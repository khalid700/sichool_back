package com.sichool.project.service;

import com.sichool.project.dao.NotificationRepository;
import com.sichool.project.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.security.config.web.servlet.AnonymousDsl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;


    public Mono<Notification> create(Notification notification)
    {
        notification.setId(UUID.randomUUID().toString());
        return notificationRepository.save(notification);
    }


    public Flux<Notification> getMyNotification(Authentication authentication)
    {
        var id = (String)authentication.getPrincipal();
        return notificationRepository.findAllByDestinationIdsIsContainingAndViewedByNotContaining(id , id)
                .flatMap(notification ->{
                    notification.getViewedBy().add(id);
                   return notificationRepository.save(notification);
                });
    }
}
