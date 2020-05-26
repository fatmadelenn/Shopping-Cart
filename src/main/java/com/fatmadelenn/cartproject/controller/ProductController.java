package com.fatmadelenn.cartproject.controller;

import com.fatmadelenn.cartproject.model.Product;
import com.fatmadelenn.cartproject.model.dto.ProductDTO;
import com.fatmadelenn.cartproject.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-cart")
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @PostMapping(value = "/product")
    public ResponseEntity<Object> createNewProduct(@RequestBody ProductDTO productDTO) {
        return productServiceImpl.createNewProduct(productDTO);
    }

    @GetMapping(value = "/products")
    public List<Product> getAllProduct() {
        return productServiceImpl.getAllProduct();
    }
}
