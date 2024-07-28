package com.balooba.springboot.balooba.Mappers;

import com.balooba.springboot.balooba.DTOs.Responses.FileResponse;
import com.balooba.springboot.balooba.Entities.PropertyFiles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { FileMapper.class })
public interface PropertyFilesMapper {

    FileResponse toDto(PropertyFiles files);
}
