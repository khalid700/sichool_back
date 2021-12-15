package com.sichool.project.dao;

import com.sichool.project.model.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CourseRepository extends ReactiveMongoRepository <Course, String>{

    Flux<Course> findAllByClassRoomIdOrderByCreatedAtDesc(String classId, Pageable page);
}
