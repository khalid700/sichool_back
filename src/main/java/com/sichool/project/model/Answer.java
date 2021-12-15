package com.sichool.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "answers")
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    private String id;
    private String studentId;
    private String quizId;
    private String description;
    private Set<String> attachments;
    private LocalDateTime createdAt;

    public Answer(@Nullable @JsonProperty("quizId") String quizId,
                  @Nullable @JsonProperty("description") String description,
                  @Nullable @JsonProperty("attachments") Set<String> attachments) {
        this.quizId = quizId;
        this.description = description;
        this.id = UUID.randomUUID().toString();
        this.attachments = attachments;
        this.createdAt = LocalDateTime.now();
    }
}
