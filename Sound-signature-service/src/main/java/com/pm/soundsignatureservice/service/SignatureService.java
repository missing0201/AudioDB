package com.pm.soundsignatureservice.service;

import com.pm.soundsignatureservice.mapper.SignatureMapper;
import com.pm.soundsignatureservice.model.SignatureEntity;
import com.pm.soundsignatureservice.repo.SignatureRepo;
import io.grpc.Status;
import org.springframework.stereotype.Service;
import sound_signature.*;

import java.util.NoSuchElementException;

@Service
public class SignatureService {
    private SignatureRepo signatureRepo;

    public SignatureService(SignatureRepo signatureRepo) {
        this.signatureRepo=signatureRepo;
    }

    public GetSignatureResponse getSignature(String earphoneId) {
        SignatureEntity e = signatureRepo.findById(parseId(earphoneId)).orElseThrow(() ->
                Status.NOT_FOUND.withDescription("No signature for " + earphoneId).asRuntimeException());
        return GetSignatureResponse.newBuilder().setSignature(SignatureMapper.toProto(e)).build();
    }

    public SetSignatureResponse setSignature(Signature s) {
        long id = parseId(s.getId());
        if (signatureRepo.existsById(id))
            throw Status.ALREADY_EXISTS.withDescription("Signature exists for " + id).asRuntimeException();
        return SetSignatureResponse.newBuilder().setSignature(SignatureMapper.toProto(signatureRepo.save(SignatureMapper.toEntity(id, s)))).build();
    }

    public UpdateSignatureResponse updateSignature(Signature s) {
        long id = parseId(s.getId());
        return UpdateSignatureResponse.newBuilder().setSignature(SignatureMapper.toProto(signatureRepo.save(SignatureMapper.toEntity(id, s)))).build();
    }

    private long parseId(String raw) {
        try { return Long.parseLong(raw); }
        catch (NumberFormatException e) {
            throw Status.INVALID_ARGUMENT.withDescription("Invalid id: " + raw).asRuntimeException();
        }
    }
}

