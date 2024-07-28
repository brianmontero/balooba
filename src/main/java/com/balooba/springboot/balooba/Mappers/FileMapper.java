package com.balooba.springboot.balooba.Mappers;

import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.Entities.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileMapper {

    @Mapping(target = "id", ignore = true)
    File fileResponseToFile(FileResponse response);

    FileResponse toDto(File file);

}