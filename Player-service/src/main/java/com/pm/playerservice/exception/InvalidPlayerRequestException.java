package com.pm.playerservice.exception;

public class InvalidPlayerRequestException extends RuntimeException {
    public InvalidPlayerRequestException(String message) {
        super(message);
    }
}
