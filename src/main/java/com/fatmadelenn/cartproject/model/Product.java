package com.fatmadelenn.cartproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    private String productTitle;

    private double productPrice;

    @ManyToOne
    private Category category;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("product")
    @JsonIgnore
    private List<ShoppingCart> shoppingCart;

    public Product() {
    }

    public void setShoppingCart(List<ShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Product(String productTitle, double productPrice, Category category) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.category = category;
    }

    public Product(long productId, double productPrice, Category category) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.category = category;
    }

    public Product(String productTitle, double productPrice, Category category, List<ShoppingCart> shoppingCart) {
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.category = category;
        this.shoppingCart = shoppingCart;
    }

    public Product(long productId, String productTitle, double productPrice, Category category, List<ShoppingCart> shoppingCart) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.category = category;
        this.shoppingCart = shoppingCart;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public List<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productTitle='" + productTitle + '\'' +
                ", productPrice=" + productPrice +
                ", category=" + category +
                '}';
    }
}
