package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.DTOs.Requests.FileRequest;
import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.Entities.Enums.FileType;
import com.balooba.springboot.balooba.Exceptions.GenericException;
import com.balooba.springboot.balooba.Mappers.FileMapper;
import com.balooba.springboot.balooba.Repositories.FileRepository;
import com.balooba.springboot.balooba.Services.Interfaces.FileService;
import com.balooba.springboot.balooba.Services.Interfaces.S3Service;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;
    private final S3Service s3Service;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper, S3Service s3Service) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.s3Service = s3Service;
    }

    @Override
    public FileResponse upload(MultipartFile file, String path, Boolean executeSave) {
        try {
            File fileToUpload = convertMultifileToFile(file);
            FileResponse response = s3Service.uploadObject(fileToUpload, path);

            com.balooba.springboot.balooba.Entities.File fileToSave = fileMapper.fileResponseToFile(response);
            fileToSave.setFileType(getFileType(file.getContentType()));
            if (executeSave) {
                fileRepository.save(fileToSave);
            }

            return response;
        } catch (IOException exception) {
            throw new GenericException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private File convertMultifileToFile(MultipartFile file) throws BadRequestException {
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        File convertedFile = new File(file.getOriginalFilename());
        try {
            Files.copy(file.getInputStream(), convertedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("There was an error uploading the file");
        }

        return convertedFile;
    }

    public FileType getFileType(String contentType) {
        if (contentType == null) {
            return null;
        }

        return switch (contentType) {
            case "image/jpeg", "image/jpg", "image/png" -> FileType.IMAGE;
            case "application/pdf" -> FileType.DOCUMENT;
            case "video/mp4" -> FileType.VIDEO;
            default -> null;
        };
    }
}
