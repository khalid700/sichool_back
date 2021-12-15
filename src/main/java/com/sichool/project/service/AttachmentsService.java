package com.sichool.project.service;

import com.sichool.project.dao.AttachmentsRepository;
import com.sichool.project.model.Attachment;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AttachmentsService {
    private static final String BASE_URL;
    static{
        Path currentRelativePath = Paths.get("");
        BASE_URL =   currentRelativePath.toAbsolutePath().toString() + "/files/";
    }
    private final AttachmentsRepository attachmentsRepository;

    public Mono<String> create(FilePart file)
    {
        var att = new Attachment(AttachmentsService.BASE_URL + UUID.randomUUID() + file.filename());

        return attachmentsRepository.save(att)
                .map(a-> "attachments/download/" + a.getId())
                .doOnSuccess(o-> file.transferTo(Paths.get(att.getLocalUri())).subscribe());
    }

    public Mono<ResponseEntity> loadBuffer(String id)
    {
       return   attachmentsRepository.findById(id)
               .map(this::loadImage);

    }

    private  ResponseEntity loadImage(Attachment attachment) {
        String filename  = attachment.getLocalUri().substring(attachment.getLocalUri().lastIndexOf("/"));
        MediaType mediaType = MediaTypeFactory.getMediaType( filename).get();



        Path path = Paths.get(attachment.getLocalUri());
        try {
            byte[] data = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(data);

            return ResponseEntity.ok()
                    // Content-Disposition
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=" + path.getFileName().toString())
                    // Content-Type
                    .contentType(mediaType) //
                    // Content-Lengh
                    .contentLength(data.length) //
                    .body(resource);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
