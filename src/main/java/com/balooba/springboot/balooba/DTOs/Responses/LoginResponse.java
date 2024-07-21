package com.balooba.springboot.balooba.DTOs.Responses;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private String accessToken;

}
