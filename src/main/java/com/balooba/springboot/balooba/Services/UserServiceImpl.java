package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.DTOs.Requests.RegisterRequest;
import com.balooba.springboot.balooba.DTOs.Responses.RegisterResponse;
import com.balooba.springboot.balooba.Entities.User;
import com.balooba.springboot.balooba.Mappers.UserMapper;
import com.balooba.springboot.balooba.Repositories.UserRepository;
import com.balooba.springboot.balooba.Services.Interfaces.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public RegisterResponse createUser(RegisterRequest dto) {
        User user = this.userMapper.registerDTOToUser(dto);
        this.userRepository.save(user);
        return this.userMapper.userToRegisterResponse(user);
    }

    public User getUserById(int id) {
        return this.userRepository.findById((long) id).orElse(null);
    }

    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails)) {
            return null;
        }

        String username = ((UserDetails) principal).getUsername();
        return userRepository.findByEmail(username).orElse(null);
    }

}
