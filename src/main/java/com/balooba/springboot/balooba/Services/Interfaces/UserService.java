package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.DTOs.Requests.RegisterRequest;
import com.balooba.springboot.balooba.DTOs.Responses.RegisterResponse;
import com.balooba.springboot.balooba.DTOs.Responses.UserResponse;
import com.balooba.springboot.balooba.Entities.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    public User getUserById(Long id);
    public User getAuthUser();
    public UserResponse getUser();
    public UserResponse getUser(Long id);
}
