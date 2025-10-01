package com.example.invoice_app.Sevice;

import com.example.invoice_app.model.User;
import com.example.invoice_app.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginAuditService {

    @Autowired
    UserRepository userRepository;

    public void updateUserLoginDate(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        System.out.println("DEBUG: login date updated");
        user.setLoginDate(new Date());
        userRepository.save(user);
    }

}
