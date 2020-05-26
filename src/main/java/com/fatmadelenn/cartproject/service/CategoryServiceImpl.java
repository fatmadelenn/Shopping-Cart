package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.Category;
import com.fatmadelenn.cartproject.model.dto.CategoryDTO;
import com.fatmadelenn.cartproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<Object> createNewCategory(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getCategoryTitle());
        return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.OK);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category getCategory(String categoryTitle) {
        Optional<Category> category = categoryRepository.findByCategoryTitle(categoryTitle);
        if (category.isPresent()) {
            return category.get();
        }
        return null;
    }
}
