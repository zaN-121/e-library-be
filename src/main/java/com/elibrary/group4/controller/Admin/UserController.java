package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.model.User;
import com.elibrary.group4.model.request.UserRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity create(@Valid UserRequest request) throws Exception {
        User user = userService.Create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Created", user));
    }

    @GetMapping
    public ResponseEntity getAll() throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success",userService.list()));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody UserRequest request, @PathVariable("id")String id) throws Exception{
        userService.update(request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Updated",request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id")String id) throws Exception{
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Deleted", null));
    }
}
