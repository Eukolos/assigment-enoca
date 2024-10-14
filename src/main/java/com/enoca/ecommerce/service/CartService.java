package com.enoca.ecommerce.service;

import com.enoca.ecommerce.dto.AddProductRequest;
import com.enoca.ecommerce.dto.CartCustomerDto;
import com.enoca.ecommerce.dto.CartDto;
import com.enoca.ecommerce.dto.UpdateCartRequest;
import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.Customer;
import com.enoca.ecommerce.entity.ProductHolder;
import com.enoca.ecommerce.repository.CartRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository repository;
    private final ProductHolderService productHolderService;

    protected void createCartForCustomer(Customer customer) {
        repository.save(Cart.builder().customer(customer).totalPrice(BigDecimal.ZERO).build());
    }

    public CartCustomerDto getCartWithCustomer(Long id) {
        Cart cart = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + id));
        return CartCustomerDto.of(cart);
    }

    public List<CartCustomerDto> getCartsWithCustomer() {
        return CartCustomerDto.of(repository.findAll());
    }

    public Cart getCart(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + id));
    }
    public CartDto getCartDto(Long id) {
        Cart cart = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + id));
        return CartDto.of(cart);
    }

    public List<CartDto> getCarts() {
        return CartDto.of(repository.findAll());
    }

    public void emptyCart(Long id) {
        Cart cart = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + id));
        Cart emptyCart = cart.toBuilder().products(List.of()).totalPrice(BigDecimal.ZERO).build();
        repository.save(emptyCart);
    }

    // update cart
    public void addProductToCart(AddProductRequest request) {
        Cart cart = repository.findById(request.cartId()).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + request.cartId()));
        if (cart.getProducts().stream().anyMatch(productHolder -> productHolder.getProduct().getId().equals(request.productId()))) {
            ProductHolder productHolder = cart.getProducts().stream().filter(productHolder1 -> productHolder1.getProduct().getId().equals(request.productId())).findFirst().get();
            BigDecimal newTotal = cart.getTotalPrice().add(productHolder.getProduct().getPrice().multiply(BigDecimal.valueOf(request.quantity())));
            ProductHolder newProductHolder = productHolder.toBuilder().quantity(productHolder.getQuantity() + request.quantity()).build();
            if (newProductHolder.getQuantity() > productHolder.getProduct().getStock()) {
                throw new IllegalArgumentException("Stock is not enough for this product");
            }
            repository.save(cart.toBuilder()
                    .products(cart.getProducts().stream().map(productHolder1 -> productHolder1.getProduct().getId().equals(request.productId()) ? newProductHolder : productHolder1).toList())
                    .totalPrice(newTotal).build());
            return;
        }
        ProductHolder productHolder = productHolderService.addProductToCart(request, cart);
        BigDecimal newTotal = cart.getTotalPrice().add(productHolder.getProduct().getPrice().multiply(BigDecimal.valueOf(productHolder.getQuantity())));
        if (productHolder.getQuantity() > productHolder.getProduct().getStock()) {
            throw new IllegalArgumentException("Stock is not enough for this product");
        }
        repository.save(cart.toBuilder()
                .products(Stream.concat(cart.getProducts().stream(), Stream.of(productHolder))
                        .toList()).totalPrice(newTotal).build());
    }


    public void updateCart(List<UpdateCartRequest> requests, Long cartId) {
        Cart cart = repository.findById(cartId).orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + cartId));
        for (UpdateCartRequest request : requests) {

            if (cart.getProducts().stream().noneMatch(productHolder -> productHolder.getProduct().getId().equals(request.productId()))) {
                throw new EntityNotFoundException("Product not found in cart with id: " + request.productId());
            }
            ProductHolder productHolder = cart.getProducts().stream().filter(productHolder1 -> productHolder1.getProduct().getId().equals(request.productId())).findFirst().get();
            if (request.quantity() < 0 || request.quantity() > productHolder.getProduct().getStock()) {
                throw new IllegalArgumentException("Quantity can not be negative or more than stock");
            }
            if (request.quantity() == 0) {
                Cart finalCart = cart.toBuilder()
                        .products(cart.getProducts().stream().filter(productHolder1 -> !productHolder1.getProduct().getId().equals(request.productId())).toList())
                        .totalPrice(cart.getTotalPrice().subtract(productHolder.getProduct().getPrice().multiply(BigDecimal.valueOf(productHolder.getQuantity()))))
                        .build();
                repository.save(finalCart);
                return;
            }
            BigDecimal exTotal = cart.getTotalPrice().subtract(productHolder.getProduct().getPrice().multiply(BigDecimal.valueOf(productHolder.getQuantity())));
            BigDecimal newTotal = exTotal.add(productHolder.getProduct().getPrice().multiply(BigDecimal.valueOf(request.quantity())));
            ProductHolder newProductHolder = productHolder.toBuilder().quantity(request.quantity()).build();
            repository.save(cart.toBuilder()
                    .products(cart.getProducts().stream().map(productHolder1 -> productHolder1.getProduct().getId().equals(request.productId()) ? newProductHolder : productHolder1).toList())
                    .totalPrice(newTotal).build());
        }

    }

    @Transactional
    public void totalReset(Long cartId) {
        // Fetch the latest cart from the database
        Cart cart = repository.findById(cartId).orElseThrow(
                () -> new IllegalArgumentException("Cart not found"));

        // Update total price to zero and save
        repository.save(cart.toBuilder().totalPrice(BigDecimal.ZERO).build());
    }


    public void removeProductFromCart(Long productId) {

        productHolderService.deleteProductHolder(productId);

    }


}
