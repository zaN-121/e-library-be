package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.Utils.Validation.JwtUtil;
import com.elibrary.group4.exception.ForbiddenException;
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

import java.util.Optional;

@RestController
@RequestMapping("/book")
@Validated
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtUtil jwtUtil;
    @PostMapping
    public ResponseEntity createBook(@RequestHeader("Authorization") String token, @Valid BookRequest request) throws Exception {
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }
        Book book =  bookService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<Book>("Created",book));
    }

    @GetMapping
    public ResponseEntity getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "DESC") String direction,
            @RequestParam(defaultValue = "bookId") String sortBy,
            @RequestParam(defaultValue = "quadrant") String name,
            @RequestParam(defaultValue = "") String author,
            @RequestParam(defaultValue = "") String language,
            @RequestParam(defaultValue = "") String releaseYear,
            @RequestParam(defaultValue = "") String category
    ) throws Exception {
        Page<Book> books =bookService.listBooksUsingSpecification(page, size, sortBy, direction, name, author, releaseYear, language, category);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success",books));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestHeader(name = "Authorization") String token, @Valid @RequestBody BookRequest request, @PathVariable("id") String id) throws Exception{
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }
        bookService.update(request,id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Updated", request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delete(@RequestHeader(name = "Authorization") String token, @PathVariable("id")String id) throws Exception{

        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }

        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Deleted",null));
    }

    @GetMapping ("/{id}")
    public ResponseEntity findById (@PathVariable("id") String id){
        Optional<Book> book = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Find",book));
    }
}
