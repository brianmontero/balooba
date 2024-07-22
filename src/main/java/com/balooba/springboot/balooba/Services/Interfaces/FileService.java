package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.Entities.Enums.FileType;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileResponse upload(MultipartFile file, String path, Boolean executeSave);
    FileType getFileType(String contentType);

}
