package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.Product;
import com.fatmadelenn.cartproject.model.dto.ProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<Object> createNewProduct(ProductDTO productDTO);

    List<Product> getAllProduct();
}
