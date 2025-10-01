package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.LoginAttemptService;
import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                                Model model,
                                HttpSession session) {

        Boolean captchaRequired = (Boolean) session.getAttribute("captchaRequired");
        model.addAttribute("captchaRequired", captchaRequired != null ? captchaRequired : false);

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }

        // A session-beli flag törlése, hogy a következő GET-nél ne maradjon
        session.removeAttribute("captchaRequired");

        return "login";
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
