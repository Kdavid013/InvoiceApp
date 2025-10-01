package com.example.invoice_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

public record InvoiceResponseDTO(
        Long id,
        String buyer,
        LocalDate createdate,
        LocalDate duedate,
        String product,
        String comment,
        Integer price) {
}
