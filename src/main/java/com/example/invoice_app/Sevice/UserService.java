package com.example.invoice_app.Sevice;

import com.example.invoice_app.dto.CreateUserRequsetDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.model.UserRepository;
import com.example.invoice_app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<User> user = repository.findByUsername(username);
        if(user.isPresent()){
            var userObj = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole())
                    .build();
        }else{
            throw new UsernameNotFoundException(username + "not found");
        }
    }

    // felhasználó létrehozás dto segitségével
    public UserResponseDTO createUser(CreateUserRequsetDTO request){
        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(request.password());
        user.setRole(request.role());

        User saved = repository.save(user);
        return toResponse(saved);
    }

    // felhasználó lekérése Id alapján
    public UserResponseDTO getUserById(int id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    // dto mappelése User entity-vé
    private UserResponseDTO toResponse(User user){
        return new UserResponseDTO(user.getName(), user.getRole());
    }
}
