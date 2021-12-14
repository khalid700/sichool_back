package com.sichool.project.service;

import com.sichool.project.dao.AttachmentsRepository;
import com.sichool.project.model.Attachment;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class AttachmentsService {
    private static final String BASE_URL = "/files";
    private final AttachmentsRepository attachmentsRepository;

    public Mono<String> create(FilePart file)
    {
        var att = new Attachment(AttachmentsService.BASE_URL  + file.name());
        file.transferTo(Paths.get(att.getLocalUri()));

        return attachmentsRepository.save(att)
                .map(Attachment::getId);
    }

    public Mono<Resourc>
}
