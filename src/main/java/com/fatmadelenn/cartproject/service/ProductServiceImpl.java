package com.fatmadelenn.cartproject.service;

import com.fatmadelenn.cartproject.model.Product;
import com.fatmadelenn.cartproject.model.dto.ProductDTO;
import com.fatmadelenn.cartproject.repository.CategoryRepository;
import com.fatmadelenn.cartproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<Object> createNewProduct(ProductDTO productDTO) {
        if (categoryRepository.findByCategoryTitle(productDTO.getCategory().getCategoryTitle()).isPresent()) {
            Product product = new Product(productDTO.getProductTitle(), productDTO.getProductPrice(), productDTO.getCategory());
            return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Category not found!", HttpStatus.NOT_FOUND);
        }
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
