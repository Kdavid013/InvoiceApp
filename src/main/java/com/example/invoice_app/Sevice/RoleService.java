package com.example.invoice_app.Sevice;

import com.example.invoice_app.dto.RoleResponseDTO;
import com.example.invoice_app.model.Role;
import com.example.invoice_app.model.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    public RoleRepository roleRepository;

    public List<RoleResponseDTO> listAllRoles(){
        return roleRepository.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    private RoleResponseDTO toResponse(Role role){
        return new RoleResponseDTO(role.getName(), role.getId());
    }
}
