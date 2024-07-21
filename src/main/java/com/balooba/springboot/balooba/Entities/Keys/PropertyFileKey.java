package com.balooba.springboot.balooba.Entities.Keys;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PropertyFileKey implements Serializable {

    private Long propertyId;
    private Long fileId;

}
