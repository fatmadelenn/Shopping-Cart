package com.fatmadelenn.cartproject.model.dto;

import com.fatmadelenn.cartproject.model.Category;

public class ProductDTO {
    private String productTitle;
    private double productPrice;
    private Category category;

    public ProductDTO() {
    }

    public ProductDTO(String productTitle, double productPrice, Category category) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.category = category;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
