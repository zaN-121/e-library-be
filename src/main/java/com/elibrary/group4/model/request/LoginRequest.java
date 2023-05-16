package com.elibrary.group4.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LoginRequest {
    private String email;
    private String password;
}
