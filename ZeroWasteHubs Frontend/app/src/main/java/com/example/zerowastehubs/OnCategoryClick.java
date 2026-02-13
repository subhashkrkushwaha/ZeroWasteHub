package com.example.zerowastehubs;

import com.example.zerowastehubs.Model.Category;
import com.example.zerowastehubs.dto.CategoryDto;


@FunctionalInterface
public interface OnCategoryClick {
    void onClick(CategoryDto category, int position);
}