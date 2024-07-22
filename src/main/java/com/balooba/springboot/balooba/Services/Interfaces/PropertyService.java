package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.DTOs.Requests.PropertyRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PropertyService {

    public void create(PropertyRequest dto, List<MultipartFile> files);

}
