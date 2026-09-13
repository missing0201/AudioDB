package com.pm.soundsignatureservice.repo;

import com.pm.soundsignatureservice.model.SignatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepo extends JpaRepository<SignatureEntity,Long> {
}
