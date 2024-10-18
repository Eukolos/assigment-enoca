package com.enoca.ecommerce.controller;


import com.enoca.ecommerce.aop.InMemoryLogger;
import com.enoca.ecommerce.dto.ProductDto;
import com.enoca.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final InMemoryLogger log;


    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        ProductDto product = service.getProductDto(id);
        log.log("ProductController -> getProduct -> product: " + product);
        return product;
    }

    @GetMapping
    public List<ProductDto> getProducts() {
        List<ProductDto> products = service.getProducts();
        log.log("ProductController -> getProducts -> products: " + products);
        return products;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        ProductDto product = service.createProduct(productDto);
        log.log("ProductController -> createProduct -> product: " + product);
        return product;
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        ProductDto updatedProduct = service.updateProduct(productDto);
        log.log("ProductController -> updateProduct -> updatedProduct: " + updatedProduct);
        return updatedProduct;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        log.log("ProductController -> deleteProduct -> id: " + id);
        service.deleteProduct(id);
    }
}

