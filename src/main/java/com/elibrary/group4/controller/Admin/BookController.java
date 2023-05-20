package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.request.BookRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createBook(@Valid BookRequest request) throws Exception {
        Book book =  bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Book>("Created",book));
    }

    @GetMapping
    public ResponseEntity getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "bookId") String sortBy
    ) throws Exception {
        Page<Book> books =bookService.list(page, size, direction, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success",books));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody BookRequest request, @PathVariable("id") String id) throws Exception{
        bookService.update(request,id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Updated", request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id")String id) throws Exception{
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Deleted",null));
    }
}
