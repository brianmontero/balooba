package com.balooba.springboot.balooba.Controllers;

import com.balooba.springboot.balooba.DTOs.Base.ApiResponse;
import com.balooba.springboot.balooba.DTOs.Requests.PropertyRequest;
import com.balooba.springboot.balooba.DTOs.Responses.PropertyResponse;
import com.balooba.springboot.balooba.Services.Interfaces.PropertyService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity createProperty(
            @RequestPart("propertyData") PropertyRequest dto,
            @RequestPart("files") List<MultipartFile> files
    ) {
        this.propertyService.create(dto, files);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PropertyResponse>>> getAllProperties() {
        List<PropertyResponse> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(ApiResponse.send(properties));
    }
}
