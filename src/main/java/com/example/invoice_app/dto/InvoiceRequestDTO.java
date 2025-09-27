package com.example.invoice_app.dto;

public record InvoiceRequestDTO(String buyer, String createdate, String duedate, String prodcut, String comment, int price ) {
}
