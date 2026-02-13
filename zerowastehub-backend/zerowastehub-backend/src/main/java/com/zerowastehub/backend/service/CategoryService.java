package com.zerowastehub.backend.service;

import com.zerowastehub.backend.ExceptionHandling.ResourceNotFoundException;
import com.zerowastehub.backend.dto.CategoryDto;
import com.zerowastehub.backend.entity.Category;
import com.zerowastehub.backend.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    public CategoryDto saveCategory( Category category){
        Category categorys =  categoryRepository.save(category);
        return modelMapper.map(categorys,CategoryDto.class);
    }

    // CREATE
    @Transactional
    public CategoryDto create(Category category) {
        Category saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryDto.class);
    }
    @Transactional
    public CategoryDto update(Integer id, Category categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (categoryRequest.getCategoryName() != null) {
            category.setCategoryName(categoryRequest.getCategoryName());
        }

        if (categoryRequest.getImagePath() != null) {
            category.setImagePath(categoryRequest.getImagePath());
        }
       Category updated = categoryRepository.save(category);
        return modelMapper.map(updated, CategoryDto.class);
    }

    @Transactional
    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(Category -> modelMapper.map(Category, CategoryDto.class))
                .toList();
    }
    public CategoryDto getById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return modelMapper.map(category, CategoryDto.class);
    }
    private CategoryDto mapToDto(Category category) {
        CategoryDto dto = modelMapper.map(category, CategoryDto.class);
        dto.setImagePath("/image/" + category.getImagePath());
        return dto;
    }
    // For sub Category

    //  ADD THIS METHOD (ENTITY)
    public Category getEntityById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
