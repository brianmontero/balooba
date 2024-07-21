package com.balooba.springboot.balooba.Repositories;

import com.balooba.springboot.balooba.Entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
