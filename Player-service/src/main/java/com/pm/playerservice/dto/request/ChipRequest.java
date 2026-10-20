package com.pm.playerservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ChipRequest(@NotNull Long chipModelId, @Positive int quantity) {}
