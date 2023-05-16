package com.elibrary.group4.model;

import com.elibrary.group4.Utils.Constants.Role;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter @Setter
public class User {
    @Id
    @Generated(value = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "user_name",nullable = false)
    private String userName;
    @Column(name = "email", nullable = false)
    @Email
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "Role",nullable = false)
    private Role role;
}
