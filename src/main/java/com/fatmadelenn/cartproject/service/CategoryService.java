package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.Category;
import com.fatmadelenn.cartproject.model.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    ResponseEntity<Object> createNewCategory(CategoryDTO categoryDTO);

    List<Category> getAllCategory();

    Category getCategory(String categoryTitle);
}
