package com.elibrary.group4.model.response;

import lombok.Data;

@Data
public abstract class CommonResponse {
    private String status;
    private String message;
    private String code;

    public CommonResponse(String message, String code){
        this.message = message;
        this.code = code;
    }
    public CommonResponse(String message){
        this.message = message;
    }
}
