package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.Sevice.LoginAttemptService;
import com.example.invoice_app.Sevice.RoleService;
import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.dto.RoleResponseDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.security.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// sima Controller ha az oldalakat akarjuk váltani, a template mappából veszi a html fájlokat
@Controller
public class ContentController {

    @Autowired
    public UserService userService;

    @Autowired
    LoginAttemptService loginAttemptService;

    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "registration";
    }

//    boolean captchaRequired = false;
//    captchaRequired = loginAttemptService.isCaptchaRequired();
//        model.addAttribute("captchaRequired", captchaRequired);

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "captcha_required", required = false) String captchaRequired,
                                Model model) {

        // ... egyéb hibaüzenet kezelés ...
        System.out.println(captchaRequired);

        if (captchaRequired != null) {
            model.addAttribute("captchaRequired", true);
        } else {
            model.addAttribute("captchaRequired", false);
        }

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }

        return "login"; // login.html
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CustomUserDetails user = userService.loadUserByUsername(username);
        model.addAttribute("user", user);
        return "home"; // login.html
    }
}
