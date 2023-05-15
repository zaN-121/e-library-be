package com.elibrary.group4.repository;

import com.elibrary.group4.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BorrowRepository  extends JpaRepository<Borrow, String> {

}
