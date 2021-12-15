package com.sichool.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermission {
    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_DELETE("student:delete"),
    STUDENT_CREATE("student:create"),
    STUDENT_LINK_TO_CLASS("student:linkToClass"),

    DOCTOR_READ("doctor:read"),
    DOCTOR_UPDATE("doctor:update"),
    DOCTOR_DELETE("doctor:delete"),
    DOCTOR_CREATE("doctor:create"),

    PROF_READ("prof:read"),
    PROF_UPDATE("prof:update"),
    PROF_DELETE("prof:delete"),
    PROF_CREATE("prof:create"),

    COURSE_READ("course:read"),
    COURSE_UPDATE("course:update"),
    COURSE_DELETE("course:delete"),
    COURSE_CREATE("course:create"),

    QUIZ_READ("quiz:read"),
    QUIZ_UPDATE("quiz:update"),
    QUIZ_DELETE("quiz:delete"),
    QUIZ_CREATE("quiz:create"),

    CLASS_READ("class:read"),
    CLASS_UPDATE("class:update"),
    CLASS_DELETE("class:delete"),
    CLASS_CREATE("class:create");


    private final String permission;
}
