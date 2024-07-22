package com.balooba.springboot.balooba.Controllers;

import com.balooba.springboot.balooba.DTOs.Requests.PropertyRequest;
import com.balooba.springboot.balooba.Services.Interfaces.PropertyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public void createProperty(
            @RequestPart("propertyData")PropertyRequest dto,
            @RequestPart("files") List<MultipartFile> files
            ) {
        this.propertyService.create(dto, files);
    }
}
