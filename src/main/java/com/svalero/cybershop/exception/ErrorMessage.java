package com.svalero.cybershop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    public int statusCode;
    private String message;
    private Map<String, String> errors;

    public ErrorMessage(int statuscode, String message){
        this.statusCode= statuscode;
        this.message = message;
    }
}
