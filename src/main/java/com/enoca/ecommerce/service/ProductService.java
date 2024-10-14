package com.enoca.ecommerce.service;

import com.enoca.ecommerce.dto.ProductDto;
import com.enoca.ecommerce.entity.BaseEntity;
import com.enoca.ecommerce.entity.Product;
import com.enoca.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;

    public ProductDto getProductDto(Long id) {

        Product product = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return ProductDto.of(product);
    }

    public List<ProductDto> getProducts() {
        return ProductDto.of(repository.findAll());
    }
    public ProductDto createProduct(ProductDto productDto) {

        validateStock(productDto);

        Product product = repository.save(ProductDto.toEntity(productDto));
        return ProductDto.of(product);
    }

    public ProductDto updateProduct(ProductDto productDto) {

        Product product = repository.findById(productDto.id()).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productDto.id()));

        validateStock(productDto);

        Product updatedProduct = product.toBuilder()
                .name(productDto.name())
                .description(productDto.description()) // Assuming description field exists
                .price(BigDecimal.valueOf(productDto.price()))
                .stock(productDto.stock())
                .build();

        return ProductDto.of(repository.save(updatedProduct));
    }

    public Product getProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }
    public Product updateProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    protected void stockDecrease(Product product, int quantity) {
        Product updatedProduct = product.toBuilder().stock(product.getStock() - quantity).build();
        validateStock(ProductDto.of(updatedProduct));
        repository.save(updatedProduct);
    }

    private void validateStock(ProductDto productDto) {
        if (productDto.stock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }


}
