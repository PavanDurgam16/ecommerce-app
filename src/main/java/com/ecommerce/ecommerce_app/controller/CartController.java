package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.model.dto.CartDTO;
import com.ecommerce.ecommerce_app.service.CartService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        CartDTO cartDTO = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartDTO> getCartByUser(@PathVariable Long userId) {
        CartDTO cartDTO = cartService.getCartByUser(userId);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeCartItem(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.removeCartItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
