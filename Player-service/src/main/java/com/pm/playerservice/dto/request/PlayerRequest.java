package com.pm.playerservice.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DacRequestDTO.class,   name = "DAC"),
        @JsonSubTypes.Type(value = AmpRequestDTO.class,   name = "AMP"),
        @JsonSubTypes.Type(value = ComboRequestDTO.class, name = "DAC_AMP")
})
public sealed interface PlayerRequest permits DacRequestDTO, AmpRequestDTO, ComboRequestDTO {
    String brand();
    String model();
}