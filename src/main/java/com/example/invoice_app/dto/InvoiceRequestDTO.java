package com.example.invoice_app.dto;

public record InvoiceRequestDTO(Long id, String buyer, String createdate, String duedate, String product, String comment, int price ) {
}
