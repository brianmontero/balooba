package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.Services.Interfaces.S3Service;
import jakarta.annotation.PostConstruct;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;

@Service
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket-name}")
    private String BUCKET_NAME;
    @Value("${aws.s3.access-key}")
    private String ACCESS_KEY;
    @Value("${aws.s3.secret-key}")
    private String SECRET_KEY;

    private S3Client client;

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
    public FileResponse uploadObject(File file, String path) throws BadRequestException {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(path)
                .build();

        client.putObject(request, RequestBody.fromFile(file));
        String url = client.utilities().getUrl(builder -> builder.bucket(BUCKET_NAME).key(path)).toExternalForm();

        FileResponse response = new FileResponse();
        response.setUrl(url);
        response.setName(file.getName());
        response.setSize((double)file.length());

        return response;
    }

}
