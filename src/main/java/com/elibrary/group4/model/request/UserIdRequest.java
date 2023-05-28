package com.elibrary.group4.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserIdRequest {
    private String userId;
}
