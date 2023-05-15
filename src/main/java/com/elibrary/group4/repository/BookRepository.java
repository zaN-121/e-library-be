package com.elibrary.group4.repository;

import com.elibrary.group4.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BookRepository  extends JpaRepository<Book, String> {

}
