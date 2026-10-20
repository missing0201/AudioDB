package com.pm.playerservice.repository;

import com.pm.playerservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepo extends JpaRepository<Brand,Long> {
    Optional<Brand> findByNameIgnoreCase(String name);
}
