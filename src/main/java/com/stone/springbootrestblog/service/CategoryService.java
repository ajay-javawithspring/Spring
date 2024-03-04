package com.stone.springbootrestblog.service;

import com.stone.springbootrestblog.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto retrieveCategoryById(long categoryId);

    List<CategoryDto> retrieveAllCategories();

    CategoryDto updateCategory(CategoryDto categoryDto, long categoryId);

    void deleteCategory(long categoryId);
}
