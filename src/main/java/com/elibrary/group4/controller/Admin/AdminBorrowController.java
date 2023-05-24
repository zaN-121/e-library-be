package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.Borrow;
import com.elibrary.group4.model.request.BookRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/borrow")
@Validated
public class AdminBorrowController  {

    @Autowired
    BorrowService borrowService;


    @PutMapping("/val/{id}")
    ResponseEntity update(@PathVariable("id") String id){

        borrowService.adminValidateBorrow(id);
        Borrow borrow = borrowService.findById(id).get();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SuccessResponse<Borrow>("Updated",borrow));
    }

    @PutMapping("/return/{id}")
    ResponseEntity returnBook(@PathVariable("id") String id){
        borrowService.adminReturnedBook(id);
        Borrow borrow = borrowService.findById(id).get();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SuccessResponse<Borrow>("Updated",borrow));
    }


}
