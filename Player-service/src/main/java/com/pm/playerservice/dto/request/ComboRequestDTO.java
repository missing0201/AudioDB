package com.pm.playerservice.dto.request;

import com.pm.playerservice.dto.request.PlayerRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComboRequestDTO(@NotBlank String brand, @NotBlank String model,
                              @NotNull @Valid DacSpecRequest dacSpec,
                              @NotNull @Valid AmpSpecRequest ampSpec) implements PlayerRequest {}