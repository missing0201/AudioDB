package com.pm.soundsignatureservice.mapper;

import com.pm.soundsignatureservice.model.SignatureEntity;
import sound_signature.Signature;
import sound_signature.SoundSignature;

public class SignatureMapper {

    // proto message -> DB entity
    public static SignatureEntity toEntity(Long id, Signature signature) {
        SignatureEntity s = new SignatureEntity();
        s.setEarphoneId(id);
        s.setPrimarySignature(signature.getPrimarySignature().toString());
        s.setBassScore(signature.getBassScore());
        s.setMidsScore(signature.getMidsScore());
        s.setTrebleScore(signature.getTrebleScore());
        s.setDescription(signature.getDescription());
        return s;
    }

    // DB entity -> proto message
    public static Signature toProto(SignatureEntity e) {
        return Signature.newBuilder()
                .setId(String.valueOf(e.getEarphoneId()))
                .setPrimarySignature(toEnum(e.getPrimarySignature()))
                .setBassScore(e.getBassScore())
                .setMidsScore(e.getMidsScore())
                .setTrebleScore(e.getTrebleScore())
                .setDescription(e.getDescription() == null ? "" : e.getDescription())
                .build();
    }

    // stored string -> proto enum
    public static SoundSignature toEnum(String value) {
        try {
            SoundSignature result = SoundSignature.valueOf(value);
            return result == SoundSignature.UNRECOGNIZED
                    ? SoundSignature.SOUND_SIGNATURE_UNSPECIFIED : result;
        } catch (IllegalArgumentException | NullPointerException e) {
            return SoundSignature.SOUND_SIGNATURE_UNSPECIFIED;
        }
    }
}
