package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.DTOs.Responses.UserResponse;
import com.balooba.springboot.balooba.Entities.User;
import com.balooba.springboot.balooba.Exceptions.GenericException;
import com.balooba.springboot.balooba.Mappers.UserMapper;
import com.balooba.springboot.balooba.Repositories.UserRepository;
import com.balooba.springboot.balooba.Services.Interfaces.UserService;
import org.springframework.http.HttpStatus;
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

    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
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

    public UserResponse getUser() {
        User user = getAuthUser();
        return userMapper.userToUserResponse(user);
    }

    public UserResponse getUser(Long id) {
        if (id == null) {
            throw new GenericException("User ID is not valid", HttpStatus.BAD_REQUEST);
        }
        User user = getUserById(id);
        if (user == null) {
            throw new GenericException("User does not exist", HttpStatus.BAD_REQUEST);
        }
        return userMapper.userToUserResponse(user);
    }
}
