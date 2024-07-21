package com.balooba.springboot.balooba.DTOs.Responses;

import lombok.Data;

@Data
public class FileResponse {

    private Long id;
    private String name;
    private String url;
    private Double size;

}
