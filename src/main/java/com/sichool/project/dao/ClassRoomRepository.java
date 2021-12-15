package com.sichool.project.dao;

import com.sichool.project.model.ClassRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface ClassRoomRepository extends ReactiveMongoRepository<ClassRoom , String>{
    Flux<ClassRoom> findAllByProofIdsIsContainingOrderByCreateAtDesc(String proofId, Pageable page);
    Flux<ClassRoom> findAllByStudentIdsIsContainingOrderByCreateAtDesc(String studentId, Pageable page);
    Flux<ClassRoom> findAllByCreateAtBeforeOrderByCreateAtDesc(LocalDateTime now , Pageable page);
}
