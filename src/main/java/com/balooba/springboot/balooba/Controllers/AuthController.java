package com.balooba.springboot.balooba.Controllers;

import com.balooba.springboot.balooba.DTOs.Requests.LoginRequest;
import com.balooba.springboot.balooba.DTOs.Requests.RegisterRequest;
import com.balooba.springboot.balooba.DTOs.Responses.LoginResponse;
import com.balooba.springboot.balooba.DTOs.Responses.RegisterResponse;
import com.balooba.springboot.balooba.Services.Interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest dto) {
        return ResponseEntity.ok(authService.signUp(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.logIn(dto));
    }
}
