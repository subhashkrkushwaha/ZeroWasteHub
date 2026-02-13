package com.zerowastehub.backend.controller;

import com.zerowastehub.backend.dto.CategoryDto;
import com.zerowastehub.backend.entity.Category;
import com.zerowastehub.backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;


    public static final String uploadDirectory="C:\\Users\\santa\\Downloads\\zerowastehub-backend\\zerowastehub-backend\\allImage";
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/save")
    public CategoryDto saveCategory(
            @RequestParam("categoryName") String categoryName,
            @RequestParam("image") MultipartFile file) throws IOException {

        String originalFileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDirectory, originalFileName);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setImagePath(originalFileName);

        return categoryService.create(category);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public CategoryDto updateCategory(
            @PathVariable Integer id,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) throws IOException {
        Category category = new Category();
        category.setCategoryName(categoryName);
        if (file != null) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDirectory , fileName);
            Files.write(path, file.getBytes());
            category.setImagePath(fileName);
        }
        return categoryService.update(id, category);
    }
    // GET ALL
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/get-all")
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAll();
    }
    // GET BY ID
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/getById/{id}")
    public CategoryDto getCategoryById(@PathVariable Integer id) {
        return categoryService.getById(id);
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        categoryService.delete(id);
        return "Category deleted successfully";
    }
}
