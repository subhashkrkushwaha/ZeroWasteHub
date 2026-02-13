package com.zerowastehub.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer id;
       @Column(nullable = false)
       private String categoryName;
       // store file name or path
       @Column(nullable = false)
       private String imagePath;
       @Column(name = "created_at",updatable = false)
       private LocalDateTime createdAt;

       @Column(name = "updated_at")
       private LocalDateTime updatedAt;

       // Automatically set createdAt before insert
       @PrePersist
       protected void onCreate() {
              this.createdAt = LocalDateTime.now();
       }

       // Automatically set updatedAt before update
       @PreUpdate
       protected void onUpdate() {
              this.updatedAt = LocalDateTime.now();
       }

}

       //       @Lob
//       @Column(nullable = false,columnDefinition = "LONGBLOB")
//       private byte[] image;
       /*     in java
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
       byte[] imageBytes = baos.toByteArray(); */

