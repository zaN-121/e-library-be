package com.elibrary.group4.service;

import com.elibrary.group4.Utils.Validation.JwtUtil;
import com.elibrary.group4.exception.DuplicateException;
import com.elibrary.group4.exception.NonAuthorizedException;
import com.elibrary.group4.exception.NotFoundException;
import com.elibrary.group4.model.User;
import com.elibrary.group4.repository.AuthRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public User register(User user){
        try {
            User getUser = authRepository.findByUserName(user.getUserName());
            if (getUser != null) {
                throw new DuplicateException("Username already exist");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return authRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public String login(User user){
        try {
            User getUser = authRepository.findByUserName(user.getUserName());
            if (getUser == null) {
                throw new NonAuthorizedException("Credential Wrong");
            }
            if (passwordEncoder.matches(user.getPassword(), getUser.getPassword())) {
                String token = jwtUtil.generateToken(getUser.getUserId(), getUser.getRole());
                return token;
            }

            throw new NonAuthorizedException("Username dan Password salah");

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
