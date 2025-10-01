package com.example.invoice_app;

import com.example.invoice_app.Sevice.RoleService;
import com.example.invoice_app.dto.RoleResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RoleServiceTest {

    @Autowired
    RoleService roleService;

    @Test
    public void testListAllRoles(){
        List<RoleResponseDTO> roleResponseDTOS = roleService.listAllRoles();

        Assertions.assertEquals(3, roleResponseDTOS.size());
        Assertions.assertEquals("ADMIN", roleResponseDTOS.get(2).name());
    }
}

