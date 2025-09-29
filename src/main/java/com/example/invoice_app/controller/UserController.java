package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.dto.CreateUserRequestDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// json és szöveg típusú végpontok használatához RestController
@RestController
public class UserController {

    // létrehozzuk a userService-t hogy képesek legyünk az adatbázisba elmenteni a felhasználót
    @Autowired
    private UserService userService;

    // végpont bekötve a /registration-re, json típusú fájlt vár
    @PostMapping(value = "/registration")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDTO user){
        try{
            UserResponseDTO response = userService.createUser(user);
            return ResponseEntity.ok(response);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


    }


}
