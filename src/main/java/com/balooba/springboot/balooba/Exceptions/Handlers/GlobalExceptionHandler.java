package com.balooba.springboot.balooba.Exceptions.Handlers;

import com.balooba.springboot.balooba.DTOs.Base.ApiResponse;
import com.balooba.springboot.balooba.Exceptions.GenericException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenericException(GenericException error, WebRequest request) {
        ApiResponse<?> response = new ApiResponse<>();
        response.setError(true);
        response.setMessage(error.getMessage());
        response.setStatus(error.getStatus());
        response.setData(null);
        return ResponseEntity.status(error.getStatus()).contentType(MediaType.APPLICATION_JSON).body(response);
    }

}
