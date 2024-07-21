package com.balooba.springboot.balooba.Entities;

import com.balooba.springboot.balooba.Entities.Base.BaseTrackingEntity;
import com.balooba.springboot.balooba.Entities.Enums.FileType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "files")
@Data
public class File extends BaseTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String name;
    private FileType fileType;

    @OneToMany(mappedBy = "file")
    private Set<PropertyFile> propertyFiles;

}
