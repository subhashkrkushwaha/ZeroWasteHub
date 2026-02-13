package com.zerowastehub.backend.dto;


import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryDto {

    private Integer id;
    private String subCategoryName;
    private String image;
    private Integer categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

