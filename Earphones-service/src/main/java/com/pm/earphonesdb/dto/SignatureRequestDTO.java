package com.pm.earphonesdb.dto;

import jakarta.validation.constraints.NotNull;

public record SignatureRequestDTO(
                                  String primarySignature,
                                  float bassScore,
                                  float midsScore,
                                  float trebleScore,
                                  String description) {
}
