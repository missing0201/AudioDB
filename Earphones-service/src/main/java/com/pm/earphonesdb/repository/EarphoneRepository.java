package com.pm.earphonesdb.repository;

import com.pm.earphonesdb.model.Earphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarphoneRepository extends JpaRepository<Earphone,Long> {
    boolean existsByBrandAndModel(String brand, String model);
    boolean existsByBrandAndModelAndIdNot(String brand, String model, Long id);
}
