package com.elibrary.group4.service;

import com.elibrary.group4.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UploadService implements IUploadService{

    private FileRepository fileRepository;

    public UploadService(@Autowired FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public String uploadMaterial(MultipartFile multipartFile) {
        return fileRepository.store(multipartFile);
    }

    @Override
    public Resource downloadMaterial(String filename) {
        return fileRepository.load(filename);
    }
}
