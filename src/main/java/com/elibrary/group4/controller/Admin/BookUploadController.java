package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.Utils.Validation.JwtUtil;
import com.elibrary.group4.exception.ForbiddenException;
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

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity upload(@RequestHeader("Authorization") String token, FormDataWithFile formDataWithFile) {
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }
        MultipartFile file = formDataWithFile.getFile();
        uploadService.uploadMaterial(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Success upload file", file.getOriginalFilename()));
    }

    @GetMapping
    public ResponseEntity download(@RequestParam String filename) {
        Resource file = uploadService.downloadMaterial(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/{name}")
    public ResponseEntity show(@PathVariable("name") String filename) throws IOException {
        Resource file = uploadService.downloadMaterial(filename);
        byte[] imageBytes = StreamUtils.copyToByteArray(file.getInputStream());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }

}