package com.zerowastehub.backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;
    private String image;
    private BigDecimal price;
    private String title;
    private String details;
    private String description;
    private String location;

    private Integer categoryId;

    private Integer subCategoryId;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
