package com.sichool.project.dao;

import com.sichool.project.model.Attachment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AttachmentsRepository  extends ReactiveMongoRepository<Attachment, String> {
}
