package com.sichool.project.service;

import com.sichool.project.dao.ClassRoomRepository;
import com.sichool.project.dao.CourseRepository;
import com.sichool.project.model.Account;
import com.sichool.project.model.ClassRoom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jmx.export.metadata.ManagedNotification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClassRoomService {
    private final ClassRoomRepository classRoomRepository;
    private final AccountService accountService;
    private final CourseRepository courseRepository;

    public Mono<ClassRoom> create(ClassRoom _class) {
        return classRoomRepository.save(_class);
    }

    public Mono<ClassRoom> addProofToClassRoomById(String classId, Set<String> proofIds) {
        return classRoomRepository.findById(classId)
                .map(c -> {
                    c.getProofIds().addAll(proofIds);
                    return c;
                }).flatMap(classRoomRepository::save);
    }

    public Mono<ClassRoom> addStudentsToClassRoom(String classId, Set<String> studentsIds) {
        return classRoomRepository.findById(classId)
                .map(c -> {
                    c.getStudentIds().addAll(studentsIds);
                    return c;
                }).flatMap(classRoomRepository::save);
    }

    public Flux<ClassRoom> findAllMyClassRooms(Authentication authentication, Pageable page)
    {
        var account =(Account) authentication.getCredentials();
        switch (account.getRole()) {
            case ADMIN:
            case DOCTOR:
                return classRoomRepository.findAllByCreateAtBeforeOrderByCreateAtDesc(LocalDateTime.now(), page);
            case STUDENT:
                return classRoomRepository
                        .findAllByStudentIdsIsContainingOrderByCreateAtDesc((String) authentication.getPrincipal(), page);
            case PROF:
                return classRoomRepository
                        .findAllByProofIdsIsContainingOrderByCreateAtDesc((String) authentication.getPrincipal(), page);

        }
        return Flux.empty();
    }
    public Flux<ClassRoom> findAllMyClassRoomsInjectCourses(Authentication authentication, Pageable page)
    {
        var account =(Account) authentication.getCredentials();
        switch (account.getRole()) {
            case ADMIN:
            case DOCTOR:
                return classRoomRepository.findAllByCreateAtBeforeOrderByCreateAtDesc(LocalDateTime.now(), page)
                        .flatMap(_c-> injectCourses(_c, page));
            case STUDENT:
                return classRoomRepository
                        .findAllByStudentIdsIsContainingOrderByCreateAtDesc((String) authentication.getPrincipal(), page)
                        .flatMap(_c-> injectCourses(_c, page));
            case PROF:
                return classRoomRepository
                        .findAllByProofIdsIsContainingOrderByCreateAtDesc((String) authentication.getPrincipal(), page)
                        .flatMap(_c-> injectCourses(_c, page));

        }
        return Flux.empty();
    }

    private Mono<ClassRoom> injectCourses(ClassRoom classRoom, Pageable page) {
        return  courseRepository.findAllByClassRoomIdOrderByCreatedAtDesc(classRoom.getId(),page )
                .collectList()
                .map(list->{
                    classRoom.setCourses(list);
                            return classRoom;
                });
    }


    public Mono<ClassRoom> getById(String id) {
        return classRoomRepository.findById(id)
                .flatMap(this::injectStudents)
                .flatMap(this::injectProofs);
    }

    private Mono<ClassRoom> injectProofs(ClassRoom classRoom) {
        return accountService.findAccountsIn(classRoom.getProofIds())
                .collectList()
                .map(accounts -> {
                    classRoom.setProofs(accounts);
                    return classRoom;
                });
    }

    private Mono<ClassRoom> injectStudents(ClassRoom classRoom) {
        return accountService.findAccountsIn(classRoom.getStudentIds())
                .collectList()
                .map(accounts -> {
                    classRoom.setStudents(accounts);
                    return classRoom;
                });
    }
}
