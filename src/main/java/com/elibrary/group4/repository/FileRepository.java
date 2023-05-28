package com.elibrary.group4.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileRepository implements IFileRepository {

    private final Path root;

    public FileRepository(@Value("/home/user/e-library-be/assets") String rootPath) {
        this.root = Paths.get(rootPath);
    }

    @Override
    public String store(MultipartFile file) {
        try {
            long epochTimeSeconds = System.currentTimeMillis() / 1000;
            String fileName = epochTimeSeconds+file.getOriginalFilename();
            Path filePath = root.resolve(fileName);
            System.out.println(filePath);
            Files.copy(file.getInputStream(), filePath);
            return "http://localhost:8080/book-file/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error " + e.getMessage());
        }
    }
}
