package com.balooba.springboot.balooba.Mappers;

import com.balooba.springboot.balooba.DTOs.Requests.PropertyRequest;
import com.balooba.springboot.balooba.Entities.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    PropertyMapper instance = Mappers.getMapper(PropertyMapper.class);

    @Mapping(target = "id", ignore = true)
    Property propertyRequestToProperty(PropertyRequest dto);

}
