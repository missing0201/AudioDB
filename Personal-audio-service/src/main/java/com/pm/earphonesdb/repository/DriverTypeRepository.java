package com.pm.earphonesdb.repository;

import com.pm.earphonesdb.model.DriverType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverTypeRepository extends JpaRepository<DriverType, Long> {
    boolean existsByNameAndIdNot(String driverTypeName, Long id);
    boolean existsByName(String driverTypeName);
}