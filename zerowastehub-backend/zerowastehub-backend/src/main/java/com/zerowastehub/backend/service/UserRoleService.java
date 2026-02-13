package com.zerowastehub.backend.service;

import com.zerowastehub.backend.ExceptionHandling.ResourceNotFoundException;
import com.zerowastehub.backend.dto.UserRoleDto;
import com.zerowastehub.backend.entity.UserRole;
import com.zerowastehub.backend.repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public UserRoleDto createRole(UserRoleDto dto) {
        UserRole role = modelMapper.map(dto, UserRole.class);
        return modelMapper.map(userRoleRepository.save(role), UserRoleDto.class);
    }

    @Transactional
    public UserRoleDto updateRole(Integer id, UserRoleDto dto) {
        UserRole role = userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        role.setUserRole(dto.getUserRole());
        return modelMapper.map(userRoleRepository.save(role), UserRoleDto.class);
    }

    @Transactional
    public void deleteRole(Integer id) {
        if(!userRoleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found");
        }
        userRoleRepository.deleteById(id);
    }


    public UserRoleDto getRoleById(Integer id) {
        return userRoleRepository.findById(id)
                .map(role -> modelMapper.map(role, UserRoleDto.class))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }


    public List<UserRoleDto> getAllRoles() {
        return userRoleRepository.findAll()
                .stream()
                .map(role -> modelMapper.map(role, UserRoleDto.class))
                .collect(Collectors.toList());
    }

}
