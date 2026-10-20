package com.pm.playerservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record DacSpecRequest(
        @NotNull @Positive Integer bitDepth,
        boolean overSampling,
        @Valid List<ChipRequest> chips) {

    public DacSpecRequest {
        chips = chips == null ? List.of() : chips;
    }
}