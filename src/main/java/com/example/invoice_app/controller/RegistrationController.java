package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.dto.CreateUserRequsetDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.model.UserRepository;
import com.example.invoice_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserService userService;

    // jelszó titkosátás
    @Autowired
    private PasswordEncoder passwordEncoder;

    // végpont bekötve a /registration-re, json típusú fájlt vár
    @PostMapping(value = "/registration")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequsetDTO user){
        UserResponseDTO response = userService.createUser(user);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
