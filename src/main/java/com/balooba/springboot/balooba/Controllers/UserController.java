package com.balooba.springboot.balooba.Controllers;

import com.balooba.springboot.balooba.DTOs.Base.ApiResponse;
import com.balooba.springboot.balooba.DTOs.Responses.UserResponse;
import com.balooba.springboot.balooba.Entities.User;
import com.balooba.springboot.balooba.Services.Interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.send(user));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> createUser() {
        UserResponse result = userService.getUser();
        return ResponseEntity.ok(ApiResponse.send(result));
    }

}
