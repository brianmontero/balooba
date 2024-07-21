package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileService {

    FileResponse upload(MultipartFile file);

}
