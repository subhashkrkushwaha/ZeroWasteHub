package com.zerowastehub.backend.service;

import com.zerowastehub.backend.ExceptionHandling.ResourceNotFoundException;
import com.zerowastehub.backend.dto.ProductDto;
import com.zerowastehub.backend.entity.Product;
import com.zerowastehub.backend.entity.User;
import com.zerowastehub.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubCategoryService subCategoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public ProductDto createProduct(
            String title, String details, String description, String location,
            BigDecimal price, Integer categoryId, Integer subCategoryId, String image,String userEmail
    ) {
        User user = userService.getEntityByUserEamil(userEmail);
        Product product = new Product();
        product.setUser(user);
        product.setTitle(title);
        product.setDetails(details);
        product.setDescription(description);
        product.setLocation(location);
        product.setPrice(price);
        product.setImage(image);
        product.setCategory(categoryService.getEntityById(categoryId));
        product.setSubCategory(subCategoryService.getEntityById(subCategoryId));
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Transactional
    public ProductDto updateProduct(
            Integer id, String title, String details, String description,
            String location, BigDecimal price,
            Integer categoryId, Integer subCategoryId, String image
    ) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setTitle(title);
        product.setDetails(details);
        product.setDescription(description);
        product.setLocation(location);
        product.setPrice(price);
        if (image != null) {
            product.setImage(image);
        }
        product.setCategory(categoryService.getEntityById(categoryId));
        product.setSubCategory(subCategoryService.getEntityById(subCategoryId));
        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(Product -> modelMapper.map(Product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return modelMapper.map(product, ProductDto.class);
    }

// self
    public List<ProductDto> getAllProductsByUserId(String  userEmail) {
        User user = userService.getEntityByUserEamil(userEmail);
        List<Product> products = productRepository.findByUserId(user.getId());

        return products.stream()
            .map(product -> modelMapper.map(product, ProductDto.class))
            .collect(Collectors.toList());
    }
    @Transactional
    public void deleteOwnProduct(Integer id,String userEmail) {
        User user = userService.getEntityByUserEamil(userEmail);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if(!product.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this product");
        }
        productRepository.delete(product);
    }

}
