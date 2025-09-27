package com.example.invoice_app.dto;

import java.util.List;

public record CreateUserRequestDTO(String name, String username, String password, List<String> roles) {
}
