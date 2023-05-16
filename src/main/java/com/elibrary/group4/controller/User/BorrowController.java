package com.elibrary.group4.controller.User;

import com.elibrary.group4.model.Borrow;
import com.elibrary.group4.model.request.BorrowRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.BorrowService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
@Validated
public class BorrowController {
    @Autowired
    BorrowService borrowService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody BorrowRequest request) throws Exception {
        Borrow borrow = modelMapper.map(request,Borrow.class);
        Borrow create = borrowService.add(borrow);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Created",create));
    }

    @GetMapping
    public ResponseEntity get() throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success",borrowService.findAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody BorrowRequest request, @PathVariable("id") String id) throws Exception{
        Borrow borrow = modelMapper.map(request, Borrow.class);
        Borrow update = borrowService.update(id,borrow);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Updated",update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id")String id) throws Exception{
        borrowService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Deleted",null));
    }
}
