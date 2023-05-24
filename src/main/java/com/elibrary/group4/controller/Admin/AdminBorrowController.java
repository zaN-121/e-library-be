package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.Utils.Validation.JwtUtil;
import com.elibrary.group4.exception.ForbiddenException;
import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.Borrow;
import com.elibrary.group4.model.request.BookRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.BorrowService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/borrow")
@Validated
public class AdminBorrowController  {

    @Autowired
    BorrowService borrowService;

    @Autowired
    JwtUtil jwtUtil;


    @PutMapping("/val/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity update(@RequestHeader(name = "Authorization") String token, @PathVariable("id") String id){
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }
        borrowService.adminValidateBorrow(id);
        Borrow borrow = borrowService.findById(id).get();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SuccessResponse<Borrow>("Updated",borrow));
    }

    @PutMapping("/return/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity returnBook(@RequestHeader("Authorization") String token, @PathVariable("id") String id){
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }

        borrowService.adminReturnedBook(id);
        Borrow borrow = borrowService.findById(id).get();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SuccessResponse<Borrow>("Updated",borrow));
    }


}
