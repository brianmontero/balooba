package com.balooba.springboot.balooba.DTOs.Base;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private Boolean error;
    private T data;

    public static <T> ApiResponse<T> send(T data, HttpStatus status, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(status);
        response.setData(data);
        response.setError(false);
        response.setMessage(message);
        return response;
    }
    public static <T> ApiResponse<T> send(T data, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(status);
        response.setData(data);
        response.setError(false);
        response.setMessage("Ok");
        return response;
    }
    public static <T> ApiResponse<T> send(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK);
        response.setData(data);
        response.setError(false);
        response.setMessage("Ok");
        return response;
    }
}
