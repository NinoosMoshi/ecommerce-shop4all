package com.ninos.category.controller;

import com.ninos.category.dtos.CategoryDTO;
import com.ninos.category.entity.Category;
import com.ninos.category.service.CategoryService;
import com.ninos.response.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<CategoryDTO> allCategories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("retrieved categories", allCategories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error", e.getMessage()));
        }
    }



    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO addCategory = categoryService.addCategory(categoryDTO);
            return ResponseEntity.ok(new ApiResponse("added category", addCategory));
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("already added category", e.getMessage()));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDTO theCategory = categoryService.findCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("added category", theCategory));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Category not found", e.getMessage()));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        try {
            CategoryDTO theCategory = categoryService.updateCategory(categoryDTO, id);
            return ResponseEntity.ok(new ApiResponse("update category successfully", theCategory));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            CategoryDTO theCategory = categoryService.findCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Category founded", theCategory));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Category Deleted successfully", null));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }




}
