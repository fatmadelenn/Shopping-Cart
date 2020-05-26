package com.fatmadelenn.cartproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long shoppingCartId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("shoppingCart")
    private List<CartInfo> cartInfos;

    public ShoppingCart() {
    }

    public long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public ShoppingCart(long shoppingCartId, List<CartInfo> cartInfos) {
        this.shoppingCartId = shoppingCartId;
        this.cartInfos = cartInfos;
    }

    public List<CartInfo> getCartInfos() {
        return cartInfos;
    }

    public void setCartInfos(List<CartInfo> cartInfos) {
        this.cartInfos = cartInfos;
    }
}
