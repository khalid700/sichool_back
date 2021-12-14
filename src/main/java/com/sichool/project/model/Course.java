package com.sichool.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Document(collection = "courses")
public class Course {
    private  String id;
    private String name;
    private String description;
    private Set<String> attachmentsUrls;
    private LocalDateTime createdAt;

    public Course(@Nullable @JsonProperty("name") String name,
                  @Nullable @JsonProperty("description") String description,
                  @Nullable @JsonProperty("attachments_urls") Set<String> attachmentsUrls) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.name = name;
        this.description = description;
        this.attachmentsUrls = attachmentsUrls;
    }
}
