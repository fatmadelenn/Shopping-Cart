package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.Coupon;
import com.fatmadelenn.cartproject.model.dto.ShoppingCartDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ShoppingCartService {

    ResponseEntity<Object> addItem(ShoppingCartDTO shoppingCartDTO);

    ResponseEntity<Object> updateCart(long shoppingCartId, ShoppingCartDTO shoppingCartDTO);

    double getTotalPrice(long shoppinggCartId);

    double getTotalAmountAfterDiscounts(long shoppingCartId);

    double getCouponDiscount(long shoppingCardId);

    double getCampaignDiscount(long shoppingCardId);

    double getDeliveryCost(long shoppinggCartId);

    double applyDiscounts(List<Double> campainDiscountList);

    double applyCoupon(double total, Coupon coupon);

    Map<String, Object> print(long shoppingCartId);
}
