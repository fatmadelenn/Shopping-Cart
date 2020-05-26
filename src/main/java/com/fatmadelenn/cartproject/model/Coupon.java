package com.fatmadelenn.cartproject.model;

public class Coupon {

    private double price;
    private double discount;
    private DiscountType discountType;

    public Coupon(double price, double discount, DiscountType discountType) {
        this.price = price;
        this.discount = discount;
        this.discountType = discountType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
