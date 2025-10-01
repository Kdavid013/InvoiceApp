package com.example.invoice_app;

import com.example.invoice_app.Sevice.UserService;
import com.example.invoice_app.dto.CreateUserRequestDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.model.Role;
import com.example.invoice_app.model.RoleRepository;
import com.example.invoice_app.model.User;
import com.example.invoice_app.security.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testLoadUserByUsername(){

        CustomUserDetails userDetails = userService.loadUserByUsername("Admin");

        Assertions.assertEquals("Admin", userDetails.getName());
    }

    @Test
    public void testCreateUser(){
        Role roleUser= roleRepository.findAll().get(1);

        List<String> roles = new ArrayList<String>();

        roles.add("ACCOUNTANT");
        CreateUserRequestDTO userDTO = new CreateUserRequestDTO(
                "David",
                "David",
                "123465789",
                roles
        );

        UserResponseDTO savedUser = userService.createUser(userDTO);

        assert savedUser.getRoles().size() == 1 ;
        Assertions.assertEquals("David", savedUser.getName());
    }

    @Test
    public void testGetUserById(){

        UserResponseDTO testUser = userService.getUserById(1);


        Assertions.assertEquals("Admin", testUser.getName());
    }

    @Test
    public void testListAllUsers(){
        List<UserResponseDTO> testUsers = userService.listAllUsers();

        for(UserResponseDTO user : testUsers){
            System.out.println(user.getId() + " " + user.getName());
        }

        System.out.println("DEBUG: " + testUsers.get(0).getName());
        System.out.println("DEBUG: " + testUsers.get(1).getName());
        System.out.println("DEBUG: " + testUsers.get(2).getName());

        Assertions.assertEquals("Admin", testUsers.get(0).getName());
        Assertions.assertEquals("User", testUsers.get(1).getName());
        Assertions.assertEquals("Accountant", testUsers.get(2).getName());
    }

    @Test
    public void testDeleteUserById(){
        userService.deleteUserById(1);

        Assertions.assertEquals(2, userService.listAllUsers().size());
    }

    @Test
    public void testUserSetRoles(){

        List<String> roles = new ArrayList<String>();

        roles.add("ACCOUNTANT");
        roles.add("ADMIN");
        userService.userSetRoles(1, roles);

        UserResponseDTO user = userService.getUserById(1);


        Assertions.assertEquals(2, user.getRoles().size());
    }
}
