package com.sichool.project.controller;

import com.sichool.project.model.Account;
import com.sichool.project.model.ClassRoom;
import com.sichool.project.model.Course;
import com.sichool.project.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("course")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("create")
    @PreAuthorize("hasAuthority('course:create')")
    public Mono<Course> create(Authentication authentication, @RequestBody Course course)
    {
        var account = (Account) authentication.getCredentials();
        return courseService.create(account,course);
    }
    @GetMapping("fetch/all/my/quiz")
    public Flux<ClassRoom> fetchAllMyQuizes(Authentication authentication,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size)
    {
        Pageable paging = PageRequest.of(page, size);
        return courseService.findAllMyQuizes(authentication, paging);
    }

    @GetMapping("fetch/by/id/{id}")
    @PreAuthorize("hasAuthority('course:read')")
    public Mono<Course> findById(@PathVariable("id") String id)
    {
        return courseService.getById(id);
    }
    @GetMapping("fetch/by/classRoom/{classId}")
    @PreAuthorize("hasAuthority('course:read')")
    public Flux<Course> findByClassRoom(@PathVariable("classId") String classId,
                                        @RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size)
    {
        Pageable paging = PageRequest.of(page, size);
        return courseService.findAllByClassRoomId(classId, paging);
    }

}
