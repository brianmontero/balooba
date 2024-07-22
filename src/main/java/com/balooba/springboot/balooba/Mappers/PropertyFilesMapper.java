package com.balooba.springboot.balooba.Mappers;

import com.balooba.springboot.balooba.Entities.PropertyFiles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyFilesMapper {

    PropertyFilesMapper instance = Mappers.getMapper(PropertyFilesMapper.class);

}
