package com.example.invoice_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Invoice {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;

    @NotBlank
    private String buyername;

    @NotBlank
    @DateTimeFormat
    private LocalDate createdate;

    @NotBlank
    @DateTimeFormat
    private LocalDate duedate;

    @NotBlank
    private String product;

    private String comment;

    @NotBlank
    private int price;

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }
}
