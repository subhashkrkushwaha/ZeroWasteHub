package com.zerowastehub.backend.dto.auth;

import com.zerowastehub.backend.entity.UserRole;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String token;
    private String email;
//    private String role;
//      @ManyToMany
//      private List<String> userRoles;

}

