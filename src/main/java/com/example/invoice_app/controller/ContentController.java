package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.InvoiceService;
import com.example.invoice_app.Sevice.LoginAttemptService;
import com.example.invoice_app.Sevice.RoleService;
import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.dto.InvoiceRequestDTO;
import com.example.invoice_app.dto.RoleResponseDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
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
    public InvoiceService invoiceService;

    @Autowired
    public UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    LoginAttemptService loginAttemptService;

    @GetMapping("/registration")
    public String showRegistrationPage(){
        return "registration";
    }

    @GetMapping("/login")
    public String showLoginPage( Model model, HttpServletRequest request) {

        String lastUsername = (String) request.getSession()
                .getAttribute("SPRING_SECURITY_LAST_USERNAME");

        boolean captchaRequired = false;
        if (lastUsername != null) {
            captchaRequired = loginAttemptService.isCaptchaRequired(lastUsername);
        }
        System.out.println("CHECK captchaRequired for username: " + lastUsername);
        System.out.println("captchaRequired = " + captchaRequired);
        model.addAttribute("captchaRequired", captchaRequired);
        model.addAttribute("username", lastUsername);
        return "login"; // login.html
    }


    @GetMapping("/home")
    public String showHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        CustomUserDetails user = userService.loadUserByUsername(username); // implement this method if needed
        model.addAttribute("user", user);
        return "home"; // login.html
    }

    @GetMapping("/administration")
    public String showAdministrationPage(Model model) {
        List<UserResponseDTO> users = userService.listAllUsers();
        model.addAttribute("users", users);
        return "administration"; // login.html
    }

    @GetMapping("administration/edit/{id}")
    public String editUser(@PathVariable long id, Model model){

        UserResponseDTO user = userService.getUserById(id);
        List<RoleResponseDTO> listRoles = roleService.listAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);

        return "registrationfilled";
    }

    @PostMapping("administration/save")
    public String updateUserRoles(@RequestParam Long id, @RequestParam List<String> roles) {
        userService.userSetRoles(id, roles);
        return "redirect:/administration";
    }

    @PostMapping(value = "/invoice/create")
    public String createInvoice(@ModelAttribute InvoiceRequestDTO invoice){
        invoiceService.createInvoice(invoice);
        return "redirect:/invoices";
    }

    @PostMapping(value = "/administration/delete/{id}")
    public String deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
        return "redirect:/administration";
    }



//    @PostMapping("/login")
//    public String processLogin(@RequestParam String username, @RequestParam String password) {
//        // hitelesítés logika
//        return "/home";
//    }

}
