package com.elibrary.group4.repository;

import com.elibrary.group4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
}
