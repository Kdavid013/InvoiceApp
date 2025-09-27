package com.example.invoice_app;

import com.example.invoice_app.model.Role;
import com.example.invoice_app.model.RoleRepository;
import com.example.invoice_app.model.User;
import com.example.invoice_app.model.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateUser(){
        Role roleUser= roleRepository.findAll().get(1);

        User user = new User();
        user.setName("david");
        user.setUsername("gradon");
        user.setPassword("20202121212");
        user.addRole(roleUser);

        User savedUser = userRepository.save(user);

        assert savedUser.getRoles().size() == 1 ;
    }
}
