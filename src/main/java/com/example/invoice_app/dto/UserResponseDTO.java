package com.example.invoice_app.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UserResponseDTO(
        long id,
        @NotBlank String name,
        @NotBlank String username,
        @NotBlank Set<String> roles) {
}