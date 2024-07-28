package com.balooba.springboot.balooba.Mappers;

import com.balooba.springboot.balooba.DTOs.Requests.PropertyRequest;
import com.balooba.springboot.balooba.DTOs.Responses.PropertyResponse;
import com.balooba.springboot.balooba.Entities.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PropertyFilesMapper.class })
public interface PropertyMapper {

    @Mapping(target = "id", ignore = true)
    Property propertyRequestToProperty(PropertyRequest dto);

    PropertyResponse propertyToPropertyResponse(Property property);
}
