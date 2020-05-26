package com.fatmadelenn.cartproject.model.dto;

import com.fatmadelenn.cartproject.model.Category;
import com.fatmadelenn.cartproject.model.DiscountType;

public class CampainDTO {

    private Category category;
    private double discount;
    private double numberOfProducts;
    private DiscountType discountType;

    public CampainDTO() {
    }

    public CampainDTO(Category category, double discount, double numberOfProducts, DiscountType discountType) {
        this.category = category;
        this.discount = discount;
        this.numberOfProducts = numberOfProducts;
        this.discountType = discountType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(double numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
