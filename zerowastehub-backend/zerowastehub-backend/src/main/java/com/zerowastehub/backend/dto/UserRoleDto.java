package com.zerowastehub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto {


    private Integer id;
    private String userRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

