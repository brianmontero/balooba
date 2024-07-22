package com.balooba.springboot.balooba.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "property_files")
@Data
public class PropertyFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

}
