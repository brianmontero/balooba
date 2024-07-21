package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.DTOs.Requests.LoginRequest;
import com.balooba.springboot.balooba.DTOs.Requests.RegisterRequest;
import com.balooba.springboot.balooba.DTOs.Responses.LoginResponse;
import com.balooba.springboot.balooba.DTOs.Responses.RegisterResponse;
import com.balooba.springboot.balooba.Entities.User;
import com.balooba.springboot.balooba.Mappers.UserMapper;
import com.balooba.springboot.balooba.Repositories.UserRepository;
import com.balooba.springboot.balooba.Services.Interfaces.AuthService;
import com.balooba.springboot.balooba.Services.Interfaces.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final JWTService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserMapper userMapper, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    public RegisterResponse signUp(RegisterRequest dto) {
        User user = userMapper.registerDTOToUser(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        this.userRepository.save(user);
        return userMapper.userToRegisterResponse(user);
    }

    public LoginResponse logIn(LoginRequest dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }

        User selectedUser = userRepository.findByEmail(dto.getEmail()).orElse(null);

        if (selectedUser == null) {
            throw new UsernameNotFoundException("User was not found");
        }

        Map<String, Object> claims = jwtService.getTokenClaims(selectedUser, List.of("Id", "Name", "Description", "Price"));
        String token = jwtService.generateToken(claims, selectedUser);

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return response;
    }

}
