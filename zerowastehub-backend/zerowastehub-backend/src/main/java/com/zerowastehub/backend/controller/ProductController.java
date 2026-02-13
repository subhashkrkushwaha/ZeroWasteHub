package com.zerowastehub.backend.controller;

import com.zerowastehub.backend.dto.ProductDto;
import com.zerowastehub.backend.dto.UserDto;
import com.zerowastehub.backend.entity.User;
import com.zerowastehub.backend.service.ProductService;
import com.zerowastehub.backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('USER','ADIMN','SELLER')")
@RequestMapping("/SELLER/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    public static final String uploadDirectory =
            "C:\\Users\\santa\\Downloads\\zerowastehub-backend\\zerowastehub-backend\\allImage";

    //  CREATE PRODUCT
    @PostMapping(value = "/create-new")
    public ProductDto createProduct(
            @RequestParam String title,
            @RequestParam String details,
            @RequestParam String description,
            @RequestParam String location,
            @RequestParam BigDecimal price,
            @RequestParam Integer categoryId,
            @RequestParam Integer subCategoryId,
            @RequestParam MultipartFile image

    ) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path path = Paths.get(uploadDirectory, fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        // Get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String userEmail = userDetails.getUsername();

        return productService.createProduct(
                title, details, description, location,
                price, categoryId, subCategoryId, fileName,userEmail
        );
    }
    @PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
    public ProductDto updateProduct(
            @PathVariable Integer id,
            @RequestParam String title,
            @RequestParam String details,
            @RequestParam String description,
            @RequestParam String location,
            @RequestParam BigDecimal price,
            @RequestParam Integer categoryId,
            @RequestParam Integer subCategoryId,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {

        String fileName = null;
        if (image != null && !image.isEmpty()) {
            fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path path = Paths.get(uploadDirectory, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());
        }

        return productService.updateProduct(
                id, title, details, description, location,
                price, categoryId, subCategoryId, fileName
        );
    }

    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

//      DELETE PRODUCT BY  ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteBy/Id/{id}")
    public String deleteProductById(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "Admin Product deleted successfully";
    }

    @GetMapping("/getById/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        return productService.getProductById(id);
    }
    // SELF PRODUCT
    //GET ALL PRODUCT ONE USER
    @GetMapping("/get-all-product-own")
    public List<ProductDto> getAllProductsOwn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
       List<ProductDto>  productDtoList = productService.getAllProductsByUserId(userEmail);
       return productDtoList;
    }
    // DELETE PRODUCT BY OWN
    @DeleteMapping("/delete-own-product/{id}")
    public String deleteProductsOwn(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        productService.deleteOwnProduct(id,userEmail);
        return "Product deleted successfully";
    }
}

