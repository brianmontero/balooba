package com.balooba.springboot.balooba.DTOs.Responses;

import lombok.Data;

@Data
public class RegisterResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String email;

}
