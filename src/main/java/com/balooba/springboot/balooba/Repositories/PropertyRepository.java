package com.balooba.springboot.balooba.Repositories;

import com.balooba.springboot.balooba.Entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
