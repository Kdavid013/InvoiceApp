package com.example.invoice_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public class UserResponseDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotNull
    private Set<String> roles;


    public UserResponseDTO(Long id, String name, String username, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }
     
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}