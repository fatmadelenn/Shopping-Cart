package com.fatmadelenn.cartproject.controller;

import com.fatmadelenn.cartproject.model.ShoppingCart;
import com.fatmadelenn.cartproject.model.dto.ShoppingCartDTO;
import com.fatmadelenn.cartproject.service.ShoppingCartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    @PostMapping(value = "/add")
    public ResponseEntity<Object> shoppingCartAddItem(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        return shoppingCartServiceImpl.addItem(shoppingCartDTO);
    }

    @PostMapping(value = "/add/{shoppingCartId}")
    public ResponseEntity<Object> shoppingCartsUpdate(@PathVariable("shoppingCartId") long shoppingCartId, @RequestBody ShoppingCartDTO shoppingCartDTO) {
        return shoppingCartServiceImpl.updateCart(shoppingCartId, shoppingCartDTO);
    }

    @GetMapping(value = "/total-price/{shoppingCartId}")
    public double getTotalPrice(@PathVariable("shoppingCartId") long shoppingCartId) {
        return shoppingCartServiceImpl.getTotalPrice(shoppingCartId);
    }

    @GetMapping(value = "/{shoppingCartId}")
    public ShoppingCart getShoppingCart(@PathVariable("shoppingCartId") long shoppingCartId) {
        return shoppingCartServiceImpl.getShoppingCart(shoppingCartId);
    }

    @GetMapping(value = "/total-coupon/{shoppingCartId}")
    public double getCouponDiscount(@PathVariable("shoppingCartId") long shoppingCartId) {
        return shoppingCartServiceImpl.getCouponDiscount(shoppingCartId);
    }

    @GetMapping(value = "/total-campain/{shoppingCartId}")
    public double getCampaignDiscount(@PathVariable("shoppingCartId") long shoppingCartId) {
        return shoppingCartServiceImpl.getCampaignDiscount(shoppingCartId);
    }

    @GetMapping(value = "/print/{shoppingCartId}")
    public Map<String, Object> print(@PathVariable("shoppingCartId") long shoppingCartId) {
        return shoppingCartServiceImpl.print(shoppingCartId);
    }
}
