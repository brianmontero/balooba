package com.balooba.springboot.balooba.DTOs.Requests;

import com.balooba.springboot.balooba.Entities.Property;
import lombok.Data;

@Data
public class PropertyRequest {

    private String name;
    private String description;
    private Double price;

}
