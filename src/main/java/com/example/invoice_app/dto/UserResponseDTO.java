package com.example.invoice_app.dto;

import java.util.Set;

public record UserResponseDTO(long id, String name, String username,  Set<String> roles) {
}