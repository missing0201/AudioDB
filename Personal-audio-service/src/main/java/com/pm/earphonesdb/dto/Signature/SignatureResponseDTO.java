package com.pm.earphonesdb.dto.Signature;

import sound_signature.SoundSignature;

public record SignatureResponseDTO(
        String id,
        SoundSignature primarySignature,
        float bassScore,
        float midsScore,
        float trebleScore,
        String description
) {}
