package com.elibrary.group4.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryRequest {
    @NotBlank(message = "{invalid.category.name.required}")
    private String name;
}
