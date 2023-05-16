package com.elibrary.group4.model.response;

public class ErrorResponse extends CommonResponse{
    public ErrorResponse(String message, String code) {
        super(code,message);
        super.setStatus("Failed");
    }
}
