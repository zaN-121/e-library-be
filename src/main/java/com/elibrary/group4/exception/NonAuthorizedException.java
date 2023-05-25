package com.elibrary.group4.exception;

public class NonAuthorizedException extends RuntimeException{
    public NonAuthorizedException(String message) {
        super(message);
    }
}
