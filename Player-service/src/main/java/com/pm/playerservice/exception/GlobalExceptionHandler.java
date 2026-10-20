package com.pm.playerservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePlayerNotFoundException(PlayerNotFoundException ex) {
        log.warn("Player Not Found{}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Player Not Found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlePlayerAlreadyExistsException(PlayerAlreadyExistsException ex) {
        log.warn("Player Already Exist{}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Player Already Exist!");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleBrandNotFoundException(BrandNotFoundException ex) {
        log.warn("Brand Not Found{}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Brand Not Found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(ChipModelNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleChipModelNotFoundException(ChipModelNotFoundException ex) {
        log.warn("Chip Model Not Found{}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Chip Model Not Found!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
