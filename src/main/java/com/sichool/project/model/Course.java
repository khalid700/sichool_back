package com.sichool.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "courses")
public class Course {
    @Id
    private  String id;
    private String name;
    private String imageUrl;
    private String description;
    private String subject;
    private String proofId;
    private String  classRoomId;
    private Set<String> attachmentsUrls;
    @Transient
    private List<Quiz> quizList;
    private LocalDateTime createdAt;

    public Course(@Nullable @JsonProperty("name") String name,
                  @Nullable @JsonProperty("description") String description,
                  @Nullable @JsonProperty("subject") String subject,
                  @Nullable @JsonProperty("imageUrl")  String imageUrl,
                  @Nullable @JsonProperty("classRoomId") String classRoomId,
                  @Nullable @JsonProperty("attachmentsUrls") Set<String> attachmentsUrls) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.imageUrl = imageUrl;
        this.classRoomId = classRoomId;
        this.subject = subject;
        this.description = description;
        this.attachmentsUrls = attachmentsUrls;
    }
}
