package com.sichool.project.dao;

import com.sichool.project.model.Notification;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface NotificationRepository  extends ReactiveMongoRepository<Notification, String> {

    Flux<Notification> findAllByDestinationIdsIsContainingAndViewedByNotContaining(String id, String vi);
}
