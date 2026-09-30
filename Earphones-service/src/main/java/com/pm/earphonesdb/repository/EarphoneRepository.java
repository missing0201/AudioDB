package com.pm.earphonesdb.repository;

import com.pm.earphonesdb.model.Brand;
import com.pm.earphonesdb.model.Earphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarphoneRepository extends JpaRepository<Earphone,Long> {
    boolean existsByBrandAndModelIgnoreCase(Brand brand, String model);
    boolean existsByBrandAndModelIgnoreCaseAndIdNot(Brand brand, String model, Long id);
}
