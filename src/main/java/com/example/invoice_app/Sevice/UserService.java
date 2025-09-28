package com.example.invoice_app.Sevice;

import com.example.invoice_app.dto.CreateUserRequestDTO;
import com.example.invoice_app.dto.UserResponseDTO;
import com.example.invoice_app.model.Role;
import com.example.invoice_app.model.RoleRepository;
import com.example.invoice_app.model.UserRepository;
import com.example.invoice_app.model.User;
import com.example.invoice_app.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // felhasználói adat lekérés a belépésnél van használva
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            //visszaadjuk a felhasználót mint CustomUserDetails, amiben meg vannak határozva a hozzáférések
            return new CustomUserDetails(user.get());
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

        // veszi a roleokat amit kapunk a dto-bo majd egyesével megkeresi az adatbázisban.
        // A talált rekordot hozzárendeli a felhasználóhoz
        for(String roleName : request.roles()){
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found:" + roleName));
            user.addRole(role);
        }
        //elmentődik a felhasználó és visszajelzést küld
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    // felhasználó lekérése Id alapján
    public UserResponseDTO getUserById(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toResponse(user);
    }

    // User entity mappelése DTO-vé
    private UserResponseDTO toResponse(User user){
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return new UserResponseDTO(user.getId(),user.getName(), user.getUsername(), roleNames);
    }

    // lekérjük az összes felhasználót
    public List<UserResponseDTO> listAllUsers(){
        return userRepository.findAll().stream()
                // Az összes lekért felhasználóhoz hozzárendeli a toResponse metódust hogy át legyen alakítva DTO-vá
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public void userSetRoles(long id, List<String> roleNames){
        User user = userRepository.findById(id).orElseThrow();
        Set<Role> newRoles = roleNames.stream()
                .map(roleRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        user.setRoles(newRoles);
        userRepository.save(user);
    }
}
