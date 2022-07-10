package com.itvaib.multidatasource.product;

import com.itvaib.multidatasource.product.entity.Product;
import com.itvaib.multidatasource.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;


    @GetMapping("/product")
    Product getPrdduct(){
        return productRepository.findById(1).orElse(new Product());

    }
}
