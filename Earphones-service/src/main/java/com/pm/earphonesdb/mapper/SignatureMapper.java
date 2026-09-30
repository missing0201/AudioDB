package com.pm.earphonesdb.mapper;

import com.pm.earphonesdb.dto.EarphoneResponseDTO;
import com.pm.earphonesdb.dto.SignatureRequestDTO;
import com.pm.earphonesdb.dto.SignatureResponseDTO;
import sound_signature.SetSignatureResponse;
import sound_signature.Signature;
import sound_signature.SoundSignature;

public class SignatureMapper {
    public static SignatureResponseDTO toDTO(Signature signature){
        SignatureResponseDTO dto = new SignatureResponseDTO(
                signature.getId(),
                signature.getPrimarySignature(),
                signature.getBassScore(),
                signature.getMidsScore(),
                signature.getTrebleScore(),
                signature.getDescription()
        );

        return dto;
    }

    public static Signature toSignature(String id,SignatureRequestDTO signatureRequestDTO){

        Signature sig=Signature.newBuilder()
                .setId(id)
                .setPrimarySignature(mapToProtoEnum(signatureRequestDTO.primarySignature()))
                .setBassScore(signatureRequestDTO.bassScore())
                .setMidsScore(signatureRequestDTO.midsScore())
                .setTrebleScore(signatureRequestDTO.trebleScore())
                .setDescription(signatureRequestDTO.description())
                .build();

        return sig;
    }


    private static SoundSignature mapToProtoEnum(String value) {
        try {
            return SoundSignature.valueOf(value);
        } catch (IllegalArgumentException | NullPointerException e) {
            return SoundSignature.SOUND_SIGNATURE_UNSPECIFIED;
        }
    }
}
