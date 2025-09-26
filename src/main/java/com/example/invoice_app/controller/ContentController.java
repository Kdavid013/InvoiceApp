package com.example.invoice_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/hello")
    public String login(){
        return "hello";
    }
}
