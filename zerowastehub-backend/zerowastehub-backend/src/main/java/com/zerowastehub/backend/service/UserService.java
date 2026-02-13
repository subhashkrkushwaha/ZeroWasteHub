package com.zerowastehub.backend.service;


import com.zerowastehub.backend.ExceptionHandling.ResourceNotFoundException;
import com.zerowastehub.backend.dto.UserDto;
import com.zerowastehub.backend.dto.auth.LoginRequestDto;
import com.zerowastehub.backend.dto.auth.UserRegisterDto;
import com.zerowastehub.backend.entity.User;
import com.zerowastehub.backend.entity.UserRole;
import com.zerowastehub.backend.repository.UserRepository;
import com.zerowastehub.backend.repository.UserRoleRepository;
import com.zerowastehub.backend.util.JWTUtility;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtility jwtUtility;


    public Map<String, String> login(LoginRequestDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials Email"));
        String email = dto.getEmail();
        String password = dto.getPassword();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResourceNotFoundException("Invalid credentials Password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtUtility.generateToken(userDetails);

        return Map.of("token", token);
    }

    @Transactional
    public UserDto register(UserRegisterDto dto) {
        //multiple role
         List<UserRole> roles = new ArrayList<>() ;
        if (dto.getRoleIds() == null || dto.getRoleIds().isEmpty()) {
            // Load default roles
          UserRole  userRole = userRoleRepository.findByUserRole("ROLE_USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Default ROLE_USER not found"));

            UserRole sellerRole = userRoleRepository.findByUserRole("ROLE_SELLER")
                    .orElseThrow(() -> new ResourceNotFoundException("Default ROLE_SELLER not found"));

            roles.add(userRole);
            roles.add(sellerRole);

        } else {
            roles = userRoleRepository.findAllById(dto.getRoleIds());
        }
        if (roles.isEmpty()) {
            throw new ResourceNotFoundException("Roles not found");
        }
        User user = new User();
        user.setUserName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserRoles(roles);
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserDto.class);
    }
    @Transactional
    public UserDto updateUser(Integer id, UserDto dto, Integer roleId) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getName() != null) user.setUserName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());

        if (roleId != null) {
            UserRole role = userRoleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setUserRoles(List.of(role));
        }
        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserDto.class);
    }

    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return modelMapper.map(user, UserDto.class);

    }
    // product user
    public User getEntityByUserEamil(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return user;
    }
    // product user
    public UserDto getUserEamilOwn(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new ModelMapper().map(user, UserDto.class);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(User -> modelMapper.map(User,UserDto.class))
                .toList();
    }

    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

}
