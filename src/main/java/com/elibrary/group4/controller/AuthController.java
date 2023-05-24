package com.elibrary.group4.controller;

import com.elibrary.group4.Utils.Constants.Role;
import com.elibrary.group4.model.User;
import com.elibrary.group4.model.request.LoginRequest;
import com.elibrary.group4.model.request.RegisterRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest){
        registerRequest.setRole(Role.USER);
        User user = modelMapper.map(registerRequest, User.class);
        User regist = authService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<String>("Registration Success", " " + regist.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        User user = modelMapper.map(loginRequest, User.class);
        String token = authService.login(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<String>("Login Success", ("Baerer " + token)));
    }
}
