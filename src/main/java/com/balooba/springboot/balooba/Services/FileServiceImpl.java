package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.Entities.Enums.FileType;
import com.balooba.springboot.balooba.Mappers.FileMapper;
import com.balooba.springboot.balooba.Repositories.FileRepository;
import com.balooba.springboot.balooba.Services.Interfaces.FileService;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.StorageClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("${aws.s3.bucket-name}")
    private String BUCKET_NAME;
    @Value("${aws.s3.access-key}")
    private String ACCESS_KEY;
    @Value("${aws.s3.secret-key}")
    private String SECRET_KEY;

    private S3Client client;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    @PostConstruct
    private void initS3() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        client = S3Client.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Override
    public FileResponse upload(MultipartFile file) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(file.getOriginalFilename())
                    .build();

            File finalFileToUpload = convertMultifileToFile(file);
            client.putObject(request, RequestBody.fromFile(finalFileToUpload));
            String url = client.utilities().getUrl(builder -> builder.bucket(BUCKET_NAME).key(finalFileToUpload.getPath())).toExternalForm();

            FileResponse response = new FileResponse();
            response.setUrl(url);
            response.setName(finalFileToUpload.getName());
            response.setSize((double)file.getSize());

            com.balooba.springboot.balooba.Entities.File fileToSave = fileMapper.fileResponseToFile(response);
            fileToSave.setFileType(getFileType(file.getContentType()));
            fileRepository.save(fileToSave);

            return response;
        } catch (IOException exception) {
            return null;
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

    private FileType getFileType(String contentType) {
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
