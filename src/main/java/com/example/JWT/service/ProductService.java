package com.example.JWT.service;

import com.example.JWT.entity.Product;
import com.example.JWT.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public List<Product> productList(){
     return  productRepository.findAll();
    }

    public ResponseEntity<Product> addProduct(Product product){
         ;

        return ResponseEntity.ok(productRepository.save(product));
    }
}
