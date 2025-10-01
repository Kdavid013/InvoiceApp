package com.example.invoice_app.controller;

import com.example.invoice_app.Sevice.RoleService;
import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.dto.RoleResponseDTO;
import com.example.invoice_app.dto.UpdateUserRolesDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    // Szükséges Service-ek beinjektálása
    @Autowired
    public UserService userService;

    @Autowired
    RoleService roleService;


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
    public String updateUserRoles(@Valid @ModelAttribute("user") UpdateUserRolesDTO user,
                                  BindingResult bindingResult,
                                  Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("listRoles" , roleService.listAllRoles());
            return "registrationfilled";
        }

        userService.userSetRoles(user.getId(), user.getRoles());
        return "redirect:/administration";
    }

    @PostMapping(value = "/administration/delete/{id}")
    public String deleteUser(@PathVariable long id){
        userService.deleteUserById(id);
        return "redirect:/administration";
    }
}
