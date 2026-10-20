package com.pm.playerservice.dto.response;

public sealed interface PlayerResponse permits DacResponseDTO, AmpResponseDTO,ComboResponseDTO{
}
