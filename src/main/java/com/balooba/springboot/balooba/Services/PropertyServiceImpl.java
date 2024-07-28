package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.DTOs.Requests.PropertyRequest;
import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.DTOs.Responses.PropertyResponse;
import com.balooba.springboot.balooba.Entities.Enums.S3FolderPaths;
import com.balooba.springboot.balooba.Entities.File;
import com.balooba.springboot.balooba.Entities.Property;
import com.balooba.springboot.balooba.Entities.PropertyFiles;
import com.balooba.springboot.balooba.Entities.User;
import com.balooba.springboot.balooba.Mappers.FileMapper;
import com.balooba.springboot.balooba.Mappers.PropertyMapper;
import com.balooba.springboot.balooba.Repositories.FileRepository;
import com.balooba.springboot.balooba.Repositories.PropertyFilesRepository;
import com.balooba.springboot.balooba.Repositories.PropertyRepository;
import com.balooba.springboot.balooba.Services.Interfaces.FileService;
import com.balooba.springboot.balooba.Services.Interfaces.PropertyService;
import com.balooba.springboot.balooba.Services.Interfaces.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserService userService;
    private final PropertyMapper propertyMapper;
    private final FileService fileService;
    private final FileMapper fileMapper;
    private final PropertyFilesRepository propertyFilesRepository;
    private final FileRepository fileRepository;

    public PropertyServiceImpl(
            PropertyRepository propertyRepository,
            UserService userService,
            PropertyMapper propertyMapper,
            FileService fileService,
            FileMapper fileMapper,
            PropertyFilesRepository propertyFilesRepository,
            FileRepository fileRepository
    ) {
        this.propertyRepository = propertyRepository;
        this.userService = userService;
        this.propertyMapper = propertyMapper;
        this.fileService = fileService;
        this.fileMapper = fileMapper;
        this.propertyFilesRepository = propertyFilesRepository;
        this.fileRepository = fileRepository;
    }

    public void create(PropertyRequest dto, List<MultipartFile> files) {
        User seller = this.userService.getAuthUser();

        if (seller == null) {
            throw new UsernameNotFoundException("Property seller was not found");
        }

        Property property = propertyMapper.propertyRequestToProperty(dto);
        property.setSeller(seller);

        this.propertyRepository.save(property);
        List<PropertyFiles> images = processImagesUpload(property, files);
        property.setImages(images);

        this.propertyRepository.save(property);
    }

    @Override
    public List<PropertyResponse> getAllProperties() {
        List<Property> properties = propertyRepository.findAll();

        ArrayList<PropertyResponse> result = new ArrayList<PropertyResponse>();
        for (Property property : properties) {
            PropertyResponse response = propertyMapper.propertyToPropertyResponse(property);
            ArrayList<FileResponse> newFiles = new ArrayList<FileResponse>();
            for (FileResponse file : response.getImages()) {
                // TODO: refactor this, not scalable.
                newFiles.add(getPropertyFilesData(file));
            }
            response.setImages(newFiles);
            result.add(response);
        }
        return result;
    }

    private FileResponse getPropertyFilesData(FileResponse file) {
        Optional<PropertyFiles> propertyFile = propertyFilesRepository.findById(file.getId());
        if (!propertyFile.isPresent()) {
            return null;
        }
        Optional<File> result = fileRepository.findById(propertyFile.get().getFile().getId());
        if (!result.isPresent()) {
            return null;
        }
        return fileMapper.toDto(result.get());
    }

    private List<PropertyFiles> processImagesUpload(Property property, List<MultipartFile> files) {
        if (files.isEmpty()) {
            return List.of();
        }

        ArrayList<PropertyFiles> images = new ArrayList<PropertyFiles>();

        for (MultipartFile file : files) {
            Path bucketPath = Paths.get(S3FolderPaths.PROPERTIES.getPath(), property.getId().toString(), file.getOriginalFilename());
            String normalizedPath = bucketPath.toString().replace(bucketPath.getFileSystem().getSeparator(), "/");
            FileResponse fileResponse = fileService.upload(file, normalizedPath, false);

            PropertyFiles propertyFileInstance = new PropertyFiles();
            File fileToUpload = fileMapper.fileResponseToFile(fileResponse);

            fileToUpload.setFileType(fileService.getFileType(file.getContentType()));

            propertyFileInstance.setFile(fileToUpload);
            propertyFileInstance.setProperty(property);

            propertyFilesRepository.save(propertyFileInstance);
            images.add(propertyFileInstance);
        }

        return images;
    }



}
