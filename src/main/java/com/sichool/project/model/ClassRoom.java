package com.sichool.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Document(collection =  "classrooms")
@AllArgsConstructor
@Data

@NoArgsConstructor
public class ClassRoom {
    private String id;
    private String name;
    @Transient
    private List<Account> proofs;
    @Transient
    private List<Account> students;
    private Set<String> proofIds;
    private Set<String> studentIds;
    @Transient
    private List<Course> courses;
    private LocalDateTime createAt;

    @JsonCreator
    public ClassRoom(@Nullable @JsonProperty("name") String name,
                     @Nullable @JsonProperty("studentIds") Set<String> studentIds,
                      @Nullable @JsonProperty("proofIds") Set<String> proofIds
    ) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.studentIds = studentIds;
        this.proofIds = proofIds;
        this.createAt = LocalDateTime.now();
    }
}
