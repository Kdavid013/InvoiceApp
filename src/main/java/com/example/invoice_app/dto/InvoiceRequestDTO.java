package com.example.invoice_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

public class InvoiceRequestDTO {

    @NotBlank(message = "Customer cannot be empty")
    private String  buyer;

    @NotNull(message = "Creation date must be seleceted")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdate;

    @NotNull(message = "Due date must be seleceted")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate duedate;

    @NotBlank(message = "Product cannot be empty")
    private String product;
    private String comment;

    @NotNull(message = "Product cannot be empty")
    @NumberFormat
    @Positive(message = "Must be 0 or higher")
    private Integer price;

    public InvoiceRequestDTO( String buyer, LocalDate createdate, LocalDate duedate, String product, String comment, Integer price) {
        this.buyer = buyer;
        this.createdate = createdate;
        this.duedate = duedate;
        this.product = product;
        this.comment = comment;
        this.price = price;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public LocalDate getCreatedate() {
        return createdate;
    }

    public void setCreatedate(LocalDate createdate) {
        this.createdate = createdate;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
