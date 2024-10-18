package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.aop.InMemoryLogger;
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


    private final InMemoryLogger log;


    //swagger config
    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable Long id) {
        log.log("CartController -> getCart");
        return service.getCartDto(id);
    }

    @GetMapping
    public List<CartDto> getCarts() {
        List<CartDto> returnList = service.getCarts();
        log.log("CartController -> getCarts -> returnList: " + returnList);
        return returnList;
    }

    @GetMapping("/customer/{id}")
    public CartCustomerDto getCartWithCustomer(@PathVariable Long id) {
        CartCustomerDto cartWithCustomer = service.getCartWithCustomer(id);
        log.log("CartController -> getCartWithCustomer -> cartWithCustomer: " + cartWithCustomer);
        return cartWithCustomer;
    }

    @GetMapping("/customer")
    public List<CartCustomerDto> getCartsWithCustomer() {
        List<CartCustomerDto> cartsWithCustomer = service.getCartsWithCustomer();
        log.log("CartController -> getCartsWithCustomer -> cartsWithCustomer: " + cartsWithCustomer);
        return cartsWithCustomer;
    }

    @PostMapping("/{id}/product")
    public void addProductToCart(@PathVariable Long id, @RequestBody AddProductRequest request) {
        service.addProductToCart(request, id);
        log.log("CartController -> addProductToCart -> product added to cart");
    }

    @PutMapping("{id}")
    public void updateCart(@PathVariable Long id, @RequestBody List<UpdateCartRequest> request) {
        service.updateCart(request, id);
        log.log("CartController -> updateCart -> cart updated");
    }

    @DeleteMapping("/{id}")
    public void emptyCart(@PathVariable Long id) {
        service.emptyCart(id);
        log.log("CartController -> emptyCart -> cart emptied");
    }

    @DeleteMapping("/{id}/product")
    public void removeProductFromCart(@PathVariable Long id) {
        service.removeProductFromCart(id);
        log.log("CartController -> removeProductFromCart -> product removed from cart");
    }


}
