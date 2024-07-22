package com.balooba.springboot.balooba.Controllers;

import com.balooba.springboot.balooba.DTOs.Requests.FileRequest;
import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.Services.Interfaces.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<FileResponse> uploadFile(@RequestBody FileRequest dto) {
        FileResponse response = fileService.upload(dto.getFile(), dto.getPath(), true);
        return ResponseEntity.ok(response);
    }
}
