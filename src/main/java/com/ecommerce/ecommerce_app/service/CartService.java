package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.model.dto.CartDTO;

public interface CartService {

    CartDTO addToCart(Long userId, Long productId, int quantity);

    CartDTO getCartByUser(Long userId);

    void removeCartItem(Long userId, Long productId);

    void clearCart(Long userId);
}
