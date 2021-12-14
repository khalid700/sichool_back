package com.sichool.project.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Document(collection =  "classrooms")
@AllArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class ClassRoom {
    public String id;
    public String name;
    public Set<String> studentIds;
    public LocalDateTime createAt;

    @JsonCreator
    public ClassRoom(@Nullable @JsonProperty("name") String name,
                     @Nullable @JsonProperty("student_ids") Set<String> studentIds) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.studentIds = studentIds;
        this.createAt = LocalDateTime.now();
    }
}
