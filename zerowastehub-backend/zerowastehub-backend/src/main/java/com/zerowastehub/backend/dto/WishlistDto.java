package com.zerowastehub.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDto {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private LocalDateTime createdAt;

}
