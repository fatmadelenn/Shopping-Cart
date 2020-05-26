package com.fatmadelenn.cartproject.model.dto;

public class ShoppingCartDTO {

    private long productId;

    private int numberOfProduct;

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(long productId, int numberOfProduct) {
        this.productId = productId;
        this.numberOfProduct = numberOfProduct;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }
}
