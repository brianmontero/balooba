package com.balooba.springboot.balooba.Repositories;

import com.balooba.springboot.balooba.Entities.PropertyFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyFilesRepository extends JpaRepository<PropertyFiles, Long> {
}
