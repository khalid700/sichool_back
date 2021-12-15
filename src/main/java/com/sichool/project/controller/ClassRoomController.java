package com.sichool.project.controller;

import com.sichool.project.dao.ClassRoomRepository;
import com.sichool.project.model.ClassRoom;
import com.sichool.project.service.ClassRoomService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("classRoom")
@AllArgsConstructor
public class ClassRoomController {
    private final ClassRoomService classRoomService;

    @PostMapping("create")
    @PreAuthorize("hasAuthority('class:create')")
    public Mono<ClassRoom> create(@RequestBody ClassRoom classRoom)
    {
        return classRoomService.create(classRoom);
    }

    @PostMapping("link/student/{classId}")
    @PreAuthorize("hasAuthority('student:linkToClass')")
    public Mono<ClassRoom> linkStudentToClassRoom(@PathVariable("classId") String classId, Set<String> studentIds)
    {
        return classRoomService.addStudentsToClassRoom(classId, studentIds);
    }

    @PostMapping("link/proof/{classId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ClassRoom> linkProofToClassRoom(@PathVariable("classId") String classId, Set<String> proofIds)
    {
        return classRoomService.addProofToClassRoomById(classId, proofIds);
    }

    @GetMapping("fetch/all")
    @PreAuthorize("hasAuthority('class:read')")
    public Flux<ClassRoom> findAllMyClassRooms(@RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "10") Integer size,
                                               Authentication authentication)
    {
        Pageable paging = PageRequest.of(page, size);
        return classRoomService.findAllMyClassRooms(authentication, paging);
    }



}
