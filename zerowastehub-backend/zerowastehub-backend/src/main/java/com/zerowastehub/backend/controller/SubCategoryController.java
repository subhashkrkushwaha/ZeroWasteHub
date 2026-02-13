package com.zerowastehub.backend.controller;

import com.zerowastehub.backend.dto.SubCategoryDto;
import com.zerowastehub.backend.entity.Category;
import com.zerowastehub.backend.entity.SubCategory;
import com.zerowastehub.backend.service.CategoryService;
import com.zerowastehub.backend.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/sub-category")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private CategoryService categoryService;

    private static final String UPLOAD_DIR = "C:/Users/santa/Downloads/zerowastehub-backend/zerowastehub-backend/allImage";

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<SubCategoryDto> saveSubCategory(
            @RequestParam String subCategoryName,
            @RequestParam Integer categoryId,
            @RequestParam MultipartFile image) throws IOException {

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, image.getBytes());

        Category category = categoryService.getEntityById(categoryId);

        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(subCategoryName);
        subCategory.setImage(fileName); // store filename only
        subCategory.setCategory(category);

        SubCategoryDto saved = subCategoryService.save(subCategory);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<SubCategoryDto> updateSubCategory(
            @PathVariable Integer id,
            @RequestParam("subCategoryName") String subCategoryName,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) throws IOException {

        SubCategory sub = new SubCategory();
        sub.setSubCategoryName(subCategoryName);

        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            sub.setImage(fileName);
        }

        Category category = categoryService.getEntityById(categoryId);
        sub.setCategory(category);

        return ResponseEntity.ok(subCategoryService.update(id, sub));
    }


    // get all
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<SubCategoryDto>> getAllSubCategory() {
        return ResponseEntity.ok(subCategoryService.getAll());
    }
    // get by id
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/getById/{id}")
    public ResponseEntity<SubCategoryDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(subCategoryService.getById(id));
    }

    // delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteByIdSC(@PathVariable Integer id) {
        subCategoryService.delete(id);
        return ResponseEntity.ok("SubCategory deleted successfully");
    }
}
