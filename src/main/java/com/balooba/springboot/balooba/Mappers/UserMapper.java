package com.balooba.springboot.balooba.Mappers;

import com.balooba.springboot.balooba.DTOs.Requests.RegisterRequest;
import com.balooba.springboot.balooba.DTOs.Responses.RegisterResponse;
import com.balooba.springboot.balooba.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    User registerDTOToUser(RegisterRequest registerRequest);

    RegisterResponse userToRegisterResponse(User user);

}
