package com.stone.springbootrestblog.service.impl;

import com.stone.springbootrestblog.entity.Category;
import com.stone.springbootrestblog.exception.ResourceNotFoundException;
import com.stone.springbootrestblog.payload.CategoryDto;
import com.stone.springbootrestblog.repository.CategoryRepository;
import com.stone.springbootrestblog.service.CategoryService;
import jdk.jshell.execution.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private Scanner scanner;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto retrieveCategoryById(long categoryId) {
        Category category = getCategory(categoryId);
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category", "id", categoryId));
    }

    @Override
    public List<CategoryDto> retrieveAllCategories() {
        List<Category> categories= categoryRepository.findAll();
        return  categories.stream().map((category)->modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {

        //CategoryServiceImpl categoryService = new CategoryServiceImpl();
        Category category = CategoryServiceImpl.this.getCategory(categoryId);

        category.setName(categoryDto.getName());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(long categoryId) {

        Category category = getCategory(categoryId);
        categoryRepository.delete(category);

    }
}
