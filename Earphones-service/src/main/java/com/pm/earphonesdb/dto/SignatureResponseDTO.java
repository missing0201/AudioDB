package com.pm.earphonesdb.dto;

public record SignatureResponseDTO(
        String id,
        String primarySignature,
        double bassScore,
        double midsScore,
        double trebleScore,
        String description
) {}
