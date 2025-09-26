package com.example.invoice_app.controller;

import com.example.invoice_app.model.UserRepository;
import com.example.invoice_app.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// json és szöveg típusú végpontok használatához RestController
@RestController
public class RegistrationController {

    // beinjektáljuk a userrpositoryt hogy az adatokat az adatbázisba tudjuk menteni
    @Autowired
    private UserRepository userRepository;

    // jelszó titkosátás
    @Autowired
    private PasswordEncoder passwordEncoder;

    // végpont bekötve a /registration-re, json típusú fájlt vár
    @PostMapping(value = "/registration", consumes = "application/json")
    public Users createUser(@RequestBody Users user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


}
