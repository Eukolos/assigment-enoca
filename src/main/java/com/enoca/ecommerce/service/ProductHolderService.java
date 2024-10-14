package com.enoca.ecommerce.service;

import com.enoca.ecommerce.dto.AddProductRequest;
import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.Order;
import com.enoca.ecommerce.entity.Product;
import com.enoca.ecommerce.entity.ProductHolder;
import com.enoca.ecommerce.repository.ProductHolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductHolderService {
    private final ProductHolderRepository repository;
    private final ProductService productService;


    public ProductHolder addProductToCart(AddProductRequest addProductRequest, Cart cart) {
        Product product = productService.getProduct(addProductRequest.productId());
        ProductHolder productHolder = ProductHolder.builder()
                .cart(cart)
                .product(product)
                .quantity(addProductRequest.quantity())
                .price(product.getPrice())
                .build();

        return repository.save(productHolder);
    }

    public ProductHolder updateProductHolder(ProductHolder productHolder) {
        return repository.save(productHolder);
    }

    public void deleteProductHolder(Long id) {
        repository.softDeleteById(id);
    }

    public void cartToOrder(Cart cart, Order order) {
        cart.getProducts().forEach(productHolder -> {
            ProductHolder updated = productHolder.toBuilder()
                    .id(productHolder.getId())
                    .cart(null)
                    .order(order)
                    .build();
            productService.stockDecrease(productHolder.getProduct(), productHolder.getQuantity());
            repository.save(updated);
        });

    }
}
