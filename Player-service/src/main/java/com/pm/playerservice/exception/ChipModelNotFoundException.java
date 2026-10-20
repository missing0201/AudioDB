package com.pm.playerservice.exception;

import jakarta.validation.constraints.NotNull;

public class ChipModelNotFoundException extends RuntimeException {
    public ChipModelNotFoundException(@NotNull Long message) {
        super(String.valueOf(message));
    }
}
