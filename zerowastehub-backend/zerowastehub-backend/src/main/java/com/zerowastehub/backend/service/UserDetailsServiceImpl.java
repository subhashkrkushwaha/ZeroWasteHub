package com.zerowastehub.backend.service;


import com.zerowastehub.backend.entity.User;
import com.zerowastehub.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new UsernameNotFoundException("User Email not found with username: " +userEmail));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        user.getUserRoles()
                                .stream()
                                .map(   userRole -> userRole.getUserRole() ) // ROLE_USER
                                .toArray(String[]::new)
                )
                .build();
    }
}
