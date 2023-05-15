package com.elibrary.group4.repository;

import com.elibrary.group4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
}
