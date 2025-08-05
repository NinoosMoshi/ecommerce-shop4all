package com.ninos.category.service;

import com.ninos.category.dtos.CategoryDTO;
import java.util.List;

public interface CategoryService {

    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
    void deleteCategory(Long categoryId);
    List<CategoryDTO> getAllCategories();
    CategoryDTO findCategoryByName(String name);
    CategoryDTO findCategoryById(Long categoryId);

}
