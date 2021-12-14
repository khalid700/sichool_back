package com.sichool.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.repository.core.support.PropertiesBasedNamedQueries;
import org.springframework.lang.Nullable;

import java.util.Properties;
import java.util.Set;
import java.util.UUID;

@Data
@Document(collection = "quiz")
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Quiz {
    @Id
    private String id;
    private String name;
    private String description;
    private String courseId;
    private Set<String> attachmentsUrls;
    private Boolean status;

    public Quiz(@Nullable @JsonProperty("name") String name,
                @Nullable @JsonProperty("description") String description,
                @Nullable Boolean status,
                @Nullable @JsonProperty("attachments_url") Set<String > attachmentsUrls)
    {
        this.name = name;
        this.description = description;
        this.status = status;
        this.attachmentsUrls = attachmentsUrls;
        this.id = UUID.randomUUID().toString();
    }
}
