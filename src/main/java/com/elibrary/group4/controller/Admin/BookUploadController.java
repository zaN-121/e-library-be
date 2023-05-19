package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.model.request.FormDataWithFile;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/book-file")
public class BookUploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping
    public ResponseEntity upload(FormDataWithFile formDataWithFile) {
        MultipartFile file = formDataWithFile.getFile();
        uploadService.uploadMaterial(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Success upload file", file.getOriginalFilename()));
    }

    @GetMapping
    public ResponseEntity download(@RequestParam String filename) {
        Resource file = uploadService.downloadMaterial(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/book-file")
    public ResponseEntity show(@PathVariable("name") String filename) throws IOException {
        Resource file = uploadService.downloadMaterial(filename);
        byte[] imageBytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }

}