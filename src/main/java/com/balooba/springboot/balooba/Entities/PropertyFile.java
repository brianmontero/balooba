package com.balooba.springboot.balooba.Entities;

import com.balooba.springboot.balooba.Entities.Base.BaseTrackingEntity;
import com.balooba.springboot.balooba.Entities.Keys.PropertyFileKey;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "property_files")
@Data
public class PropertyFile extends BaseTrackingEntity {

    @EmbeddedId
    private PropertyFileKey id;

    @ManyToOne
    @MapsId("propertyId")
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @MapsId("fileId")
    @JoinColumn(name = "file_id")
    private File file;

}
