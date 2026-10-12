package com.pm.earphonesdb.dto;

public record SignatureRequestDTO(
                                  String primarySignature,
                                  float bassScore,
                                  float midsScore,
                                  float trebleScore,
                                  String description) {
}
