package com.example.invoice_app.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateUserRequestDTO(
        @NotBlank String name,
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank List<String> roles) {
}
