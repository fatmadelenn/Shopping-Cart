package com.fatmadelenn.cartproject.controller;

import com.fatmadelenn.cartproject.model.Category;
import com.fatmadelenn.cartproject.model.dto.CategoryDTO;
import com.fatmadelenn.cartproject.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @PostMapping(value = "/category")
    public ResponseEntity<Object> createNewCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryServiceImpl.createNewCategory(categoryDTO);
    }

    @GetMapping(value = "/categorys")
    public List<Category> getAllCategory() {
        return categoryServiceImpl.getAllCategory();
    }
}
