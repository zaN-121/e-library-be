package com.elibrary.group4.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter @Getter
public class FormDataWithFile {
    private MultipartFile file;
}
