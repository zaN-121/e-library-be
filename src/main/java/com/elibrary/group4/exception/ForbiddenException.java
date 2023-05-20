package com.elibrary.group4.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super("Forbidden");
    }
}
