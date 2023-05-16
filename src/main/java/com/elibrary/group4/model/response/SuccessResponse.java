package com.elibrary.group4.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
public class SuccessResponse<T> extends CommonResponse{
    private T data;
    public SuccessResponse(String message, T data){
        super(message);
        super.setCode("200");
        super.setStatus(HttpStatus.OK.name());
        this.data = data;
    }

}
