package com.pm.soundsignatureservice.service;

import com.pm.soundsignatureservice.model.SignatureEntity;
import com.pm.soundsignatureservice.repo.SignatureRepo;
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
        Long id = Long.parseLong(earphoneId);

        SignatureEntity entity = signatureRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Signature not found for earphoneId: " + earphoneId));

        Signature signature = Signature.newBuilder()
                .setId(String.valueOf(entity.getEarphoneId()))
                .setPrimarySignature(mapToProtoEnum(entity.getPrimarySignature()))
                .setBassScore(entity.getBassScore())
                .setMidsScore(entity.getMidsScore())
                .setTrebleScore(entity.getTrebleScore())
                .setDescription(entity.getDescription() == null ? "" : entity.getDescription())
                .build();

        GetSignatureResponse response = GetSignatureResponse.newBuilder()
                .setSignature(signature)
                .build();

        return response;
    }

    public SetSignatureResponse setSignature(Signature signature){
        System.out.println("Signature received: " + signature);
        System.out.println("ID received: [" + signature.getId() + "]");
        Long id=Long.parseLong(signature.getId());

        try{
            if(!signatureRepo.existsById(id)){
                SignatureEntity inputSignature= new SignatureEntity();

                        inputSignature.setEarphoneId(id);
                        inputSignature.setPrimarySignature(signature.getPrimarySignature().toString());
                        inputSignature.setBassScore(signature.getBassScore());
                        inputSignature.setMidsScore(signature.getMidsScore());
                        inputSignature.setTrebleScore(signature.getTrebleScore());
                        inputSignature.setDescription(signature.getDescription());

                signatureRepo.save(inputSignature);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        return  SetSignatureResponse.newBuilder()
                .setSignature(signature)
                .build();
    }

    public UpdateSignatureResponse updateSignature(Signature signature){
        Long id=Long.parseLong(signature.getId());

        try{
            if(signatureRepo.existsById(id)){
                SignatureEntity inputSignature= new SignatureEntity();

                inputSignature.setEarphoneId(id);
                inputSignature.setPrimarySignature(signature.getPrimarySignature().toString());
                inputSignature.setBassScore(signature.getBassScore());
                inputSignature.setMidsScore(signature.getMidsScore());
                inputSignature.setTrebleScore(signature.getTrebleScore());
                inputSignature.setDescription(signature.getDescription());

                signatureRepo.save(inputSignature);
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        return  UpdateSignatureResponse.newBuilder()
                .setSignature(signature)
                .build();
    }

    private SoundSignature mapToProtoEnum(String value) {
        try {
            return SoundSignature.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return SoundSignature.SOUND_SIGNATURE_UNSPECIFIED;
        }
    }
}

