package com.pm.earphonesdb.repository;

import com.pm.earphonesdb.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
