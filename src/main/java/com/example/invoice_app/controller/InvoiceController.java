package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping(value = "/invoicecreation")
    public ResponseEntity<InvoiceRequestDTO> createInvoice(@RequestBody InvoiceRequestDTO invoice){
        InvoiceRequestDTO response = invoiceService.createInvoice(invoice);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/invoices")
    public List<InvoiceRequestDTO> getInvoices(){
        return invoiceService.listAllInvoices();
    }

    @GetMapping(value = "/invoices/{id}")
    public InvoiceRequestDTO getInvoiceById(@PathVariable long id){
        return invoiceService.getInvoiceById(id);
    }

}
