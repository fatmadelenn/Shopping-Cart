package com.fatmadelenn.cartproject.model;

import java.util.HashSet;
import java.util.Set;

public class DeliveryCostCalculator {

    private double costPerDelivery;
    private double costPerProduct;
    private static final double FIXED_COST = 2.99;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    public double calculateFor(ShoppingCart cart) {
        int numberOfProducts = 0;
        int numberOfDeliveries = 0;
        Set<Long> products = new HashSet<>();
        Set<String> categories = new HashSet<>();

        for (CartInfo cartInfo : cart.getCartInfos()) {
            if (products.add(cartInfo.getProduct().getProductId())) {
                numberOfProducts++;
            }
            if (categories.add(cartInfo.getProduct().getCategory().getCategoryTitle())) {
                numberOfDeliveries++;
            }
        }

        return (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + FIXED_COST; // kategori ve ürün sayısı
    }
}
