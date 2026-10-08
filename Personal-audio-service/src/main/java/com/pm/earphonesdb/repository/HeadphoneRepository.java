package com.pm.earphonesdb.repository;

import com.pm.earphonesdb.model.Headphone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadphoneRepository extends JpaRepository<Headphone,Long> {
}
