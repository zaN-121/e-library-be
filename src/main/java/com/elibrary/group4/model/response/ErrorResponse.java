package com.elibrary.group4.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class ErrorResponse extends CommonResponse{
    public ErrorResponse(String message, String code) {
        super(message,code);
        super.setStatus("Failed");
    }
}
