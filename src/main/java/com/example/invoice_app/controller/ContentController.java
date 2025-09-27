package com.example.invoice_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// sima Controller ha az oldalakat akarjuk váltani, a template mappából veszi a html fájlokat
@Controller
public class ContentController {

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

//    @GetMapping("/invoice")
//    public String showInvoicesPage() {
//        return "invoice"; // login.html
//    }


//    @PostMapping("/login")
//    public String processLogin(@RequestParam String username, @RequestParam String password) {
//        // hitelesítés logika
//        return "/home";
//    }

}
