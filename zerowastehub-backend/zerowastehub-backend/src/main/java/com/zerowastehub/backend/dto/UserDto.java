package com.zerowastehub.backend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private List<UserRoleDto> userRoles;

    // Never expose password in response DTOs
//    private Integer roleId;
//    private String roleName;

//    private List<Integer> roleIds;
//    private List<String> roleNames;

}
