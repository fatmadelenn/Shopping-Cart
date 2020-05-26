package com.fatmadelenn.cartproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @Column(unique = true)
    private String categoryTitle;

    @OneToMany(mappedBy = "category")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private List<Product> products;

    @OneToOne(mappedBy = "categoryForCampain")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private Campaign campaign;

    public Category() {
    }

    public Category(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public Category(String categoryTitle, Campaign campaign) {
        this.categoryTitle = categoryTitle;
        this.campaign = campaign;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryTitle='" + categoryTitle + '\'' +
                '}';
    }
}
