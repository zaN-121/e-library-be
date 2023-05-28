package com.elibrary.group4.controller.Error;

import com.elibrary.group4.exception.ForbiddenException;
import com.elibrary.group4.exception.NonAuthorizedException;
import com.elibrary.group4.exception.NotFoundException;
import com.elibrary.group4.model.response.ErrorResponse;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(exception.getMessage(),"00"));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> HandleNotFoundException(NotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(exception.getMessage(),"404"));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse>  HandleForbiddenException(ForbiddenException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(exception.getMessage(),  "403"));
    }

    @ExceptionHandler(NonAuthorizedException.class)
    public ResponseEntity<ErrorResponse> HandleNonAuthorizedException(NonAuthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(exception.getMessage(), "401"));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> HandleJwtException(JwtException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(exception.getMessage(), "401"));
    }
}
