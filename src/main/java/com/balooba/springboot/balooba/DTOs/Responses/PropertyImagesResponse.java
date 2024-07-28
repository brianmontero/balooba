package com.balooba.springboot.balooba.DTOs.Responses;

import com.balooba.springboot.balooba.Entities.Enums.FileType;
import lombok.Data;

@Data
public class PropertyImagesResponse {

    private Long id;
    private String name;
    private String url;
    private FileType fileType;

}
