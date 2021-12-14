package com.sichool.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "attachments")
@Data
@AllArgsConstructor
public class Attachment{
    private String id;
    private String localUri;
    public Attachment(String localUri)
    {
        this.localUri = localUri;
        this.id = UUID.randomUUID().toString();
    }

}
