package com.balooba.springboot.balooba.DTOs.Requests;

import com.balooba.springboot.balooba.Entities.Enums.S3FolderPaths;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileRequest {

    private MultipartFile file;
    private String path;

}
