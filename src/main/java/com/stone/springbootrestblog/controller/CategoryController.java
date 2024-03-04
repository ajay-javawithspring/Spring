package com.stone.springbootrestblog.controller;

import com.stone.springbootrestblog.payload.CategoryDto;
import com.stone.springbootrestblog.repository.CategoryRepository;
import com.stone.springbootrestblog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "REST API for Categories"
)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(
            summary = "Create Category API",
            description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "CREATED"
    )
    @SecurityRequirement(
            name = "Jwt Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto savedCategory = categoryService.addCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve Category API",
            description = "Retrieve Category API is used to retrieve Categories Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> retrieveCategoryById(@PathVariable("id") long categoryId){
        CategoryDto category = categoryService.retrieveCategoryById(categoryId);
        return  new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve all Categories API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @GetMapping
    public ResponseEntity<List<CategoryDto>> retrieveAllCategories(){
        return  new ResponseEntity<>(categoryService.retrieveAllCategories(), HttpStatus.OK);
    }

    @Operation(
            summary = "Update Category API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @SecurityRequirement(
            name = "Jwt Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable("id") long categoryId){

        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Category API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @SecurityRequirement(
            name = "Jwt Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId){

        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>("Category deleted Successfully", HttpStatus.OK);
    }
}
