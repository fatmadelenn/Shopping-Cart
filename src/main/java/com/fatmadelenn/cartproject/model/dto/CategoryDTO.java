package com.fatmadelenn.cartproject.model.dto;

public class CategoryDTO {

    private String categoryTitle;

    public CategoryDTO() {
    }

    public CategoryDTO(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
}
