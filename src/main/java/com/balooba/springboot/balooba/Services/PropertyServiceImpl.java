package com.balooba.springboot.balooba.Services;

import com.balooba.springboot.balooba.Entities.Property;
import com.balooba.springboot.balooba.Entities.User;
import com.balooba.springboot.balooba.Repositories.PropertyRepository;
import com.balooba.springboot.balooba.Services.Interfaces.PropertyService;
import com.balooba.springboot.balooba.Services.Interfaces.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserService userService;

    public PropertyServiceImpl(
            PropertyRepository propertyRepository,
            UserService userService
    ) {
        this.propertyRepository = propertyRepository;
        this.userService = userService;
    }

    public void create() {
        User seller = this.userService.getAuthUser();

        if (seller == null) {
            throw new UsernameNotFoundException("Property seller was not found");
        }

        Property property = new Property();
        property.setName("Casa en canelones");
        property.setDescription("Excelente casa");
        property.setPrice(300000);
        property.setSeller(seller);

        this.propertyRepository.save(property);
    }

}
