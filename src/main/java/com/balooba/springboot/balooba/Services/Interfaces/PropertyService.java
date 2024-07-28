package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.DTOs.Requests.PropertyRequest;
import com.balooba.springboot.balooba.DTOs.Responses.PropertyResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {

    void create(PropertyRequest dto, List<MultipartFile> files);
    List<PropertyResponse> getAllProperties();

}
