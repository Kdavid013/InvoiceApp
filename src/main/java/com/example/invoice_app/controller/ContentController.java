package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// sima Controller ha az oldalakat akarjuk váltani, a template mappából veszi a html fájlokat
@Controller
public class ContentController {

    @Autowired
    public InvoiceService invoiceService;

    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.html
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home"; // login.html
    }

    @GetMapping("/administration")
    public String showAdministrationPage() {
        return "administration"; // login.html
    }



    @PostMapping(value = "/invoice/create")
    public String createInvoice(@ModelAttribute InvoiceRequestDTO invoice){
        invoiceService.createInvoice(invoice);
        return "redirect:/invoices";
    }

//    @PostMapping("/login")
//    public String processLogin(@RequestParam String username, @RequestParam String password) {
//        // hitelesítés logika
//        return "/home";
//    }

}
