package com.sichool.project.controller;


import com.sichool.project.dao.AttachmentsRepository;
import com.sichool.project.model.Attachment;
import com.sichool.project.service.AttachmentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("attachments")
@AllArgsConstructor
public class AttachmentsController {

    private final AttachmentsService attachmentsService;

    @PostMapping(value = "upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> upload(@RequestPart FilePart file)
    {
        return attachmentsService.create(file);
    }
    @GetMapping("download/{id}")
    public Mono<ResponseEntity> download(@PathVariable("id") String id)
    {
        return attachmentsService.loadBuffer(id);
    }
}
