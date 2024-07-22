package com.balooba.springboot.balooba.Services.Interfaces;

import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import org.apache.coyote.BadRequestException;

import java.io.File;

public interface S3Service {

    public FileResponse uploadObject(File file, String path) throws BadRequestException;

}
