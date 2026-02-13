package com.zerowastehub.backend.dto;


import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;
    private String categoryName;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
