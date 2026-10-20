package com.pm.playerservice.dto.request;

import com.pm.playerservice.model.amp.AmpClass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AmpSpecRequest(
        @NotNull @Positive Double powerOutput,
        @NotNull @Positive Integer minImpedance,
        @NotNull @Positive Integer maxImpedance,
        @NotNull AmpClass ampClass) {}