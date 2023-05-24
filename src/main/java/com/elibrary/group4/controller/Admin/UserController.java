package com.elibrary.group4.controller.Admin;

import com.elibrary.group4.Utils.Validation.JwtUtil;
import com.elibrary.group4.exception.ForbiddenException;
import com.elibrary.group4.model.User;
import com.elibrary.group4.model.request.UserRequest;
import com.elibrary.group4.model.response.SuccessResponse;
import com.elibrary.group4.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity create(@RequestHeader("Authorization") String token, @Valid UserRequest request) throws Exception {
        var tokenAndRole = jwtUtil.getRoleAndId(token);


        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }
        User user = userService.Create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Created", user));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity getAll(@RequestHeader("Authorization") String token) throws Exception{
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Success",userService.list()));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity update(@RequestHeader("Authorization") String token, @Valid @RequestBody UserRequest request, @PathVariable("id")String id) throws Exception{
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN") || !tokenAndRole.get("userId").equals(id)) {
            throw new ForbiddenException("Forbidden");
        }
        userService.update(request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("Updated",request));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity delete(@RequestHeader("Authorization") String token, @PathVariable("id")String id) throws Exception{
        var tokenAndRole = jwtUtil.getRoleAndId(token);

        if (!tokenAndRole.get("role").equals("ADMIN")) {
            throw new ForbiddenException("Forbidden");
        }
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>("Deleted", null));
    }
}
