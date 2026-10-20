package com.pm.earphonesdb.repository;

import com.pm.earphonesdb.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepo extends JpaRepository<Driver,Long> {
    public boolean existsByEarphoneIdAndDriverTypeId(Long earphoneId,Long driverTypeId);
}
