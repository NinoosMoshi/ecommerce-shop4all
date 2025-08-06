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
            List<CategoryDTO> allCategories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("retrieved categories", allCategories));
    }



    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDTO categoryDTO) {
            CategoryDTO addCategory = categoryService.addCategory(categoryDTO);
            return ResponseEntity.ok(new ApiResponse("added category", addCategory));
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
            CategoryDTO theCategory = categoryService.findCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("added category", theCategory));
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
            CategoryDTO theCategory = categoryService.updateCategory(categoryDTO, id);
            return ResponseEntity.ok(new ApiResponse("update category successfully", theCategory));
    }


    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
            CategoryDTO theCategory = categoryService.findCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Category founded", theCategory));
    }


    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Category Deleted successfully", null));
    }




}
