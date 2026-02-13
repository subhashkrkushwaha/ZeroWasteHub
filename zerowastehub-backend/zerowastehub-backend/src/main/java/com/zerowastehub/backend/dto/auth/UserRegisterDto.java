package com.zerowastehub.backend.dto.auth;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDateTime createdAt;
//    private Integer roleId;
    private List<Integer> roleIds;

}

