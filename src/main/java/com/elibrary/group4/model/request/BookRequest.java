package com.elibrary.group4.model.request;

import com.elibrary.group4.Utils.Constants.IsAvailable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter @Setter
public class BookRequest {
    @NotBlank(message = "{invalid.title.required}")
    private String name;
    private MultipartFile thumbnail;
    private String  thumbnailUrl;
    @NotBlank(message = "{invalid.author.name.required}")

    private String author;
//    @NotBlank(message = "Page is required")
    private Integer page;
    private  String language;

    @NotBlank(message = "Release Year is required")
    private String releaseYear;
//    @NotBlank(message = "{invalid.is.available.required}")
//    private IsAvailable isAvailable;
//    @NotBlank(message = "{invalid.stock.required}")
    @Min(1)
    private Integer stock;
    @NotBlank(message = "{invalid.category.id.required}")
    private String categoryId;
}
