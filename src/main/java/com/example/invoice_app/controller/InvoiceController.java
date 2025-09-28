package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    @GetMapping("/invoices")
    public String showInvoicesPage(Model model) {
        List<InvoiceRequestDTO> invoices = invoiceService.listAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoices"; // login.html
    }

    @GetMapping("/invoices/{id}")
    public String showInvoiceDetailPage(@PathVariable Long id, Model model) {
        InvoiceRequestDTO invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "invoicefilled"; // login.html
    }

    @GetMapping("/invoice/create")
    public String showInvoiceCreationPage(Model model) {
        model.addAttribute("invoice", new InvoiceRequestDTO(null, "", "", "", "", "", 0));
        return "invoicecreation";
    }

}
