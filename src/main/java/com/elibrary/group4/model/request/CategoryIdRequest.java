package com.elibrary.group4.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CategoryIdRequest {
    @NotBlank(message = "{invalid.category.id.required}")
    private String categoryId;
}
