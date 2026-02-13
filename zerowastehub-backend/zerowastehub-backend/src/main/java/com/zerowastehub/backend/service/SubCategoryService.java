package com.zerowastehub.backend.service;

import com.zerowastehub.backend.ExceptionHandling.ResourceNotFoundException;
import com.zerowastehub.backend.dto.SubCategoryDto;
import com.zerowastehub.backend.entity.SubCategory;
import com.zerowastehub.backend.repository.SubCategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {

    @Autowired
    SubCategoryRepository subCatRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public SubCategoryDto save(SubCategory subCategory) {
        SubCategory saved = subCatRepository.save(subCategory);
        return modelMapper.map(saved, SubCategoryDto.class);
    }

    @Transactional
    public SubCategoryDto update(Integer id, SubCategory request) {
        SubCategory existing = subCatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found"));
        if (request.getSubCategoryName() != null) {
            existing.setSubCategoryName(request.getSubCategoryName());
        }
        if (request.getImage() != null) {
            existing.setImage(request.getImage());
        }
        if (request.getCategory() != null) {
            existing.setCategory(request.getCategory());
        }
        SubCategory updated = subCatRepository.save(existing);
        return modelMapper.map(updated, SubCategoryDto.class);
    }


    @Transactional
    public void delete(Integer id) {
       if(!subCatRepository.existsById(id)) {
           throw new ResourceNotFoundException("SubCategory not found");
       }
       subCatRepository.deleteById(id);
    }

    public List<SubCategoryDto> getAll() {
        return subCatRepository.findAll()
                .stream()
                .map(SubCategory -> modelMapper.map(SubCategory, SubCategoryDto.class))
                .toList();
    }

    public SubCategoryDto getById(Integer id) {
        SubCategory sub = subCatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found"));
        return modelMapper.map(sub, SubCategoryDto.class);
    }

    //Product
    // For sub Category
    // ✅ ADD THIS METHOD (ENTITY)
    public SubCategory  getEntityById(Integer id) {
        return subCatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
