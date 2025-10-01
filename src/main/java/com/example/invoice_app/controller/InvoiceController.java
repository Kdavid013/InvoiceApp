package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.dto.InvoiceResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/invoices")
    public String showInvoicesPage(Model model) {
        List<InvoiceResponseDTO> invoices = invoiceService.listAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoices"; // login.html
    }

    @GetMapping("/invoices/{id}")
    public String showInvoiceDetailPage(@PathVariable Long id, Model model) {
        InvoiceResponseDTO invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);
        return "invoicefilled"; // login.html
    }

    @GetMapping("/invoice/create")
    public String showInvoiceCreationPage(Model model,
                                          @ModelAttribute("invoice") InvoiceRequestDTO invoice) {
        return "invoicecreation";
    }

    @PostMapping(value = "/invoice/create")
    public String createInvoice(@Valid @ModelAttribute("invoice") InvoiceRequestDTO invoice,
                                BindingResult bindingResult,
                                Model model){

        if (bindingResult.hasErrors()) {
            // Ha van hiba, visszaküldjük a felhasználót az űrlap oldalára
            // A "bindingResult" automatikusan bekerül a Model-be, így a HTML-ben elérhető
            System.out.println(bindingResult.getAllErrors());
            return "invoicecreation";
        }

        invoiceService.createInvoice(invoice);
        return "redirect:/invoices";
    }
}
