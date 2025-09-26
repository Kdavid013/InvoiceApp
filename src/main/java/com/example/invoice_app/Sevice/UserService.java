package com.example.invoice_app.Sevice;

import com.example.invoice_app.model.UserRepository;
import com.example.invoice_app.model.Users;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    // felhasználói adat lekérés a belépésnél van használva
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = repository.findByUsername(username);
        if(user.isPresent()){
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole())
                    .build();
        }else{
            throw new UsernameNotFoundException(username + "not found");
        }


    }

}
