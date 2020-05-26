package com.fatmadelenn.cartproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class CartInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    private int numberOfProduct;

    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JsonIgnoreProperties("cartInfos")
    List<ShoppingCart> shoppingCart;

    public CartInfo() {
    }

    public CartInfo(Product product, int numberOfProduct) {
        this.product = product;
        this.numberOfProduct = numberOfProduct;
    }

    public CartInfo(Long id, Product product, int numberOfProduct, List<ShoppingCart> shoppingCart) {
        this.id = id;
        this.product = product;
        this.numberOfProduct = numberOfProduct;
        this.shoppingCart = shoppingCart;
    }

    public CartInfo(Product product, int numberOfProduct, List<ShoppingCart> shoppingCart) {
        this.product = product;
        this.numberOfProduct = numberOfProduct;
        this.shoppingCart = shoppingCart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public List<ShoppingCart> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingCart> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
