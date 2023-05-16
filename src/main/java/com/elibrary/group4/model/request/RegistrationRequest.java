package com.elibrary.group4.model.request;

import com.elibrary.group4.Utils.Constants.Role;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private String password;
    private Role role;
}
