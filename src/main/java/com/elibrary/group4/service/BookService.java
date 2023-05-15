package com.elibrary.group4.service;

import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.request.BookRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    List<Book> list() throws Exception;
    Book create(BookRequest bookRequest) throws Exception;
    Book get(String bookId) throws Exception;
    void update(BookRequest bookRequest, String bookId) throws Exception;
    void delete(String bookId) throws Exception;
    List<Book> findByTitleContains(String title);
    List<Book> findByAuthorNameContains(String authorName);
    List<Book> findByPublisherContains(String publisher);
    List<Book> findBookByCategory(String categoryName);
    List<Book> getBy(String keyword, String value) throws Exception;
    Page<Book> list(Integer page, Integer size, String direction, String sortBy) throws Exception;
}
