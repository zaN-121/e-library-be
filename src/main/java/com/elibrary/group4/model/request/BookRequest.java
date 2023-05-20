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
    private String title;
    private MultipartFile image;
    @NotBlank(message = "{invalid.author.name.required}")
    private String authorName;
    @NotBlank(message = "{invalid.publisher.required}")
    private String publisher;
//    @NotBlank(message = "{invalid.publication.year.required}")
    private LocalDate publicationYear;
//    @NotBlank(message = "{invalid.is.available.required}")
//    private IsAvailable isAvailable;
//    @NotBlank(message = "{invalid.stock.required}")
    @Min(1)
    private Integer stock;
    @NotBlank(message = "{invalid.category.id.required}")
    private String categoryId;
}
