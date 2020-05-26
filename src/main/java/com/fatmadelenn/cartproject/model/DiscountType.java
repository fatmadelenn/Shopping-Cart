package com.fatmadelenn.cartproject.model;

public enum DiscountType {
    RATE("Rate"),
    AMOUNT("Amount");

    private String type;

    DiscountType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
