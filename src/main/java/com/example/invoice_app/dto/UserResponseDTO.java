package com.example.invoice_app.dto;

import com.example.invoice_app.model.Role;

import java.util.Set;

public record UserResponseDTO(String name, Set<Role> role) {
}
