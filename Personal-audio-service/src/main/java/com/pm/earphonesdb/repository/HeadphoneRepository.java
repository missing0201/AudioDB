package com.pm.earphonesdb.repository;

import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.model.Headphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadphoneRepository extends JpaRepository<Headphone,Long> {
    boolean existsByBrandAndModelIgnoreCase(Brand brand, String model);
    boolean existsByBrandAndModelIgnoreCaseAndIdNot(Brand brand, String model, Long id);
}
