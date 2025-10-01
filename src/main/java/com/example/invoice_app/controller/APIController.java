package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.dto.CreateUserRequestDTO;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// json és szöveg típusú végpontok használatához RestController
@RestController
public class APIController {

    // létrehozzuk a userService-t hogy képesek legyünk az adatbázisba elmenteni a felhasználót
    @Autowired
    private UserService userService;

    @Autowired
    private InvoiceService invoiceService;

    // végpont bekötve a /registration-re, json típusú fájlt vár
    @PostMapping(value = "/registration")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO user) {
        try {
            UserResponseDTO response = userService.createUser(user);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping(value = "/api/invoices")
    public List<InvoiceRequestDTO> getInvoices() {
        List<InvoiceRequestDTO> invoices = invoiceService.listAllInvoices();
        return invoices;
    }

    @GetMapping(value = "/api/invoices/{id}")
    public ResponseEntity<?> getInvoiceDetail(@PathVariable long id) {
        try {
            InvoiceRequestDTO response = invoiceService.getInvoiceById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No invoice found.");
        }
    }

    @PostMapping(value = "/api/invoices/create")
    public ResponseEntity<?> invoiceCreate(@RequestBody InvoiceRequestDTO request) {
        try {
            InvoiceRequestDTO response = invoiceService.createInvoice(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Invalid invoice data.");
        }
    }

    @GetMapping("/api/administration")
    public ResponseEntity<List<UserResponseDTO>> showAdministrationPage() {
        List<UserResponseDTO> response = userService.listAllUsers();
        return ResponseEntity.ok(response); // login.html
    }

    @PostMapping("/api/administration/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody List<String> roles) {
        try {
            userService.userSetRoles(id, roles);
            UserResponseDTO response = userService.getUserById(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

    }

    @PostMapping(value = "/api/administration/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("User deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

}
