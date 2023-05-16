package com.elibrary.group4.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {
    String uploadMaterial(MultipartFile multipartFile);
    Resource downloadMaterial(String filename);
}
