package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.DTOs.Requests.LoginRequest;
import com.balooba.springboot.balooba.DTOs.Requests.RegisterRequest;
import com.balooba.springboot.balooba.DTOs.Responses.LoginResponse;
import com.balooba.springboot.balooba.DTOs.Responses.RegisterResponse;

public interface AuthService {

    public RegisterResponse signUp(RegisterRequest dto);
    public LoginResponse logIn(LoginRequest dto);

}
