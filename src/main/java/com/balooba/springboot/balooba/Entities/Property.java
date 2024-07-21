package com.balooba.springboot.balooba.Entities;

import com.balooba.springboot.balooba.Entities.Base.BaseTrackingEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name="properties")
@Data
public class Property extends BaseTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @OneToMany(mappedBy = "property")
    private Set<PropertyFile> images;

}
