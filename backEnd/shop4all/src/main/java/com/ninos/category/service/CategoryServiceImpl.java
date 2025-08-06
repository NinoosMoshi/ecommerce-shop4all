package com.ninos.category.service;

import com.ninos.category.dtos.CategoryDTO;
import com.ninos.category.entity.Category;
import com.ninos.category.exception.ResourceAlreadyExistsException;
import com.ninos.category.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        boolean existsByName = categoryRepository.existsByName(categoryDTO.getName());
        if (existsByName) {
            throw new EntityExistsException("Category '" + categoryDTO.getName() + "' already exists!");
        }
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return convertCategoryToDTO(savedCategory);
    }


    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        existingCategory.setName(categoryDTO.getName());

        Category savedCategory = categoryRepository.save(existingCategory);
        return convertCategoryToDTO(savedCategory);
    }


    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found!"));
        categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    @Override
    public CategoryDTO findCategoryByName(String name) {
        Category savedCategory = categoryRepository.findByName(name);
        return convertCategoryToDTO(savedCategory);
    }

    @Override
    public CategoryDTO findCategoryById(Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + categoryId + " not found!"));
        return convertCategoryToDTO(savedCategory);
    }


    private CategoryDTO convertCategoryToDTO(Category savedCategory) {
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }


}
