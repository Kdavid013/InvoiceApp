package com.example.invoice_app.Sevice;

import com.example.invoice_app.dto.CreateUserRequestDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.model.UserRepository;
import com.example.invoice_app.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // felhasználói adat lekérés a belépésnél van használva
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
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
    public UserResponseDTO createUser(CreateUserRequestDTO request){
        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    // felhasználó lekérése Id alapján
    public UserResponseDTO getUserById(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    // dto mappelése User entity-vé
    private UserResponseDTO toResponse(User user){
        return new UserResponseDTO(user.getName(), user.getRole());
    }
}
