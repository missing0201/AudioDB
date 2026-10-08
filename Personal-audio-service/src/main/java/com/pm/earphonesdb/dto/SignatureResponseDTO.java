package com.pm.earphonesdb.dto;

import sound_signature.SoundSignature;

public record SignatureResponseDTO(
        String id,
        SoundSignature primarySignature,
        float bassScore,
        float midsScore,
        float trebleScore,
        String description
) {}
