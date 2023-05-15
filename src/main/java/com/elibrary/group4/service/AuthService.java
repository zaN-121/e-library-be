package com.elibrary.group4.service;

import com.elibrary.group4.Utils.Validation.JwtUtil;
import com.elibrary.group4.model.User;
import com.elibrary.group4.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthService {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    JwtUtil jwtUtil;

    public User register(User user){
        try {
            return authRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String login(User user){
        try {
            User getUser = authRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
            String token = jwtUtil.generateToken("elibrary");
            if(user.getEmail().equals(getUser.getEmail()) && user.getPassword().equals(getUser.getPassword())){
                return token;
            }else {
                throw new RuntimeException("Email or password incorrect");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
