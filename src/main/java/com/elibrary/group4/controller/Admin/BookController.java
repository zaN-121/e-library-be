package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.request.BookRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createBook(@RequestBody BookRequest request) throws Exception {
        Book book =  bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Book>("Created",book));
    }

}
