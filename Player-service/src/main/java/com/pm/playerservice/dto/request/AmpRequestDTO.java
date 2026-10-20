package com.pm.playerservice.dto.request;

import com.pm.playerservice.dto.request.PlayerRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AmpRequestDTO(@NotBlank String brand, @NotBlank String model,
                            @NotNull @Valid AmpSpecRequest ampSpec) implements PlayerRequest {}