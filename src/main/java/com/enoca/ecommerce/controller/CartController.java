package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.dto.AddProductRequest;
import com.enoca.ecommerce.dto.CartCustomerDto;
import com.enoca.ecommerce.dto.CartDto;
import com.enoca.ecommerce.dto.UpdateCartRequest;
import com.enoca.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final CartService service;

    //swagger config
     @GetMapping("/{id}")
    public CartDto getCart(@PathVariable Long id) {
        return service.getCartDto(id);
    }

    @GetMapping
    public List<CartDto> getCarts() {
        return service.getCarts();
    }

    @GetMapping("/customer/{id}")
    public CartCustomerDto getCartWithCustomer(@PathVariable Long id) {
        return service.getCartWithCustomer(id);
    }

    @GetMapping("/customer")
    public List<CartCustomerDto> getCartsWithCustomer() {
        return service.getCartsWithCustomer();
    }

    @PostMapping("/addProduct")
    public void addProductToCart(@RequestBody AddProductRequest request) {
        service.addProductToCart(request);
    }

    @PutMapping("{id}")
    public void updateCart(@PathVariable Long id, @RequestBody List<UpdateCartRequest> request) {
        service.updateCart(request, id);
    }

    @DeleteMapping("/empty/{id}")
    public void emptyCart(@PathVariable Long id) {
        service.emptyCart(id);
    }

    @DeleteMapping("/product/{id}")
    public void removeProductFromCart(@PathVariable Long id) {
        service.removeProductFromCart(id);
    }



}
