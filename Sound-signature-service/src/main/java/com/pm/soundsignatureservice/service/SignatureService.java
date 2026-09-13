package com.pm.soundsignatureservice.service;

import com.pm.soundsignatureservice.repo.SignatureRepo;
import org.springframework.stereotype.Service;

@Service
public class SignatureService {
    private SignatureRepo signatureRepo;

    public SignatureService(SignatureRepo signatureRepo) {
        this.signatureRepo=signatureRepo;
    }
}

