package com.example.JWT.controller;

import com.example.JWT.entity.Product;
import com.example.JWT.repository.ProductRepository;
import com.example.JWT.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
@Autowired
ProductService productService;
@GetMapping("/findAll")
public List<Product> findAllProducts(){
    return productService.productList();
}

@PostMapping("/saveProduct")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
    return productService.addProduct(product);
}

}
