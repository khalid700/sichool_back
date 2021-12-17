package com.sichool.project.service;

import com.sichool.project.dao.ClassRoomRepository;
import com.sichool.project.dao.CourseRepository;
import com.sichool.project.dao.QuizRepository;
import com.sichool.project.model.Account;
import com.sichool.project.model.ClassRoom;
import com.sichool.project.model.Course;
import com.sichool.project.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final NotificationService notificationService;
    private final ClassRoomRepository classRoomRepository;
    private final ClassRoomService classRoomService;
    private final QuizRepository quizRepository;


    public Mono<Course> create(Account account, Course course)
    {
        course.setProofId(account.getId());
        return courseRepository.save(course)
                .doOnSuccess(c -> createCourseNotification(c, account));
    }

    public Mono<Set<String>> getStudentsIds(String courseId)
    {
        return getById(courseId)
                .flatMap(course->classRoomRepository.findById(course.getClassRoomId()))
                .map(ClassRoom::getStudentIds);
    }


    private void createCourseNotification(Course course, Account account) {
        classRoomRepository.findById(course.getClassRoomId())
                .map(ClassRoom::getStudentIds)
                .map(list-> Notification.builder()
                        .owner(account)
                        .destinationIds(list)
                        .status(true)
                        .type("course")
                        .viewedBy(new HashSet<>())
                        .description(String.format("new course available subject %s", course.getSubject()))
                        .build())
                .flatMap(notificationService::create)
                .subscribe();
    }

    public Mono<Course> getById(String id)
    {
        return courseRepository.findById(id);
    }
    public Flux<ClassRoom> findAllMyQuizes(Authentication authentication, Pageable page)
    {
        return classRoomService.findAllMyClassRoomsInjectCourses(authentication,page)
                .flatMap(_class -> injectQuiz(_class, page));
    }

    public Mono<ClassRoom> injectQuiz(ClassRoom _class, Pageable page)
    {
        return Flux.fromStream(_class.getCourses().stream())
                .map(course->{
                       course.setQuizList( quizRepository
                               .findAllByCourseId(course.getId())
                               .collectList().block());
                       return course;
                }).collectList()
                .map(list->{
                    _class.setCourses(list);
                    return _class;
                });
    }

    public Flux<Course> findAllByClassRoomId(String classId, Pageable page)
    {
        return courseRepository.findAllByClassRoomIdOrderByCreatedAtDesc(classId, page);
    }

}
