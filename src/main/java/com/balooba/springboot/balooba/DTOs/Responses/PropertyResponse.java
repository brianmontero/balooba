package com.balooba.springboot.balooba.DTOs.Responses;

import lombok.Data;

import java.util.List;

@Data
public class PropertyResponse {

    private String name;
    private String description;
    private Double price;
    private List<FileResponse> images;

}
