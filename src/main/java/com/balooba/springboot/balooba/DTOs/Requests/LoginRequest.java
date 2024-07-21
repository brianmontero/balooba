package com.balooba.springboot.balooba.DTOs.Requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
