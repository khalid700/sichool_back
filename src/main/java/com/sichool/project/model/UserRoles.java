package com.sichool.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import  static com.sichool.project.model.UserPermission.*;


@Getter
public enum UserRoles {
    STUDENT(Set.of(
            STUDENT_UPDATE,
            CLASS_READ,
            QUIZ_READ,
            COURSE_READ
    )),
    ADMIN(Arrays.stream(UserPermission.values()).collect(Collectors.toSet())),
    PROF(Set.of(
            STUDENT_READ,
            QUIZ_CREATE,
            QUIZ_DELETE,
            QUIZ_READ,
            QUIZ_UPDATE,
            CLASS_READ,
            CLASS_UPDATE,
            COURSE_CREATE,
            COURSE_READ,
            COURSE_UPDATE,
            COURSE_DELETE
    )),
    DOCTOR(Set.of(
            PROF_READ,
            CLASS_READ,
            CLASS_UPDATE,
            STUDENT_CREATE,
            STUDENT_READ,
            STUDENT_LINK_TO_CLASS
    ));

    UserRoles(Set<UserPermission> permissions)
    {
        this.permissions = permissions;
    }

        private final Set<UserPermission> permissions;

        public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
            Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                    .collect(Collectors.toSet());
            permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
            return permissions;
        }
    }

