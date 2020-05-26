package com.fatmadelenn.cartproject.model;


import javax.persistence.*;

@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long campainId;

    @OneToOne
    private Category categoryForCampain;

    private double discount;
    private double numberOfProducts;
    private DiscountType discountType;

    public Campaign() {
    }

    public Campaign(Category categoryForCampain, double discount, double numberOfProducts, DiscountType discountType) {
        this.categoryForCampain = categoryForCampain;
        this.discount = discount;
        this.numberOfProducts = numberOfProducts;
        this.discountType = discountType;
    }

    public Long getCampainId() {
        return campainId;
    }

    public void setCampainId(Long campainId) {
        this.campainId = campainId;
    }

    public Category getCategoryForCampain() {
        return categoryForCampain;
    }

    public void setCategoryForCampain(Category categoryForCampain) {
        this.categoryForCampain = categoryForCampain;
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
