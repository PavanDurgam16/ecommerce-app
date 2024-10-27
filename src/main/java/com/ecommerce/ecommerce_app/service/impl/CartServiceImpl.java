package com.ecommerce.ecommerce_app.service.impl;

import com.ecommerce.ecommerce_app.enums.Size;
import com.ecommerce.ecommerce_app.model.dto.CartDTO;
import com.ecommerce.ecommerce_app.model.dto.CartItemDTO;
import com.ecommerce.ecommerce_app.model.dto.ProductDTO;
import com.ecommerce.ecommerce_app.model.entity.Cart;
import com.ecommerce.ecommerce_app.model.entity.CartItem;
import com.ecommerce.ecommerce_app.model.entity.Product;
import com.ecommerce.ecommerce_app.model.entity.User;
import com.ecommerce.ecommerce_app.repository.CartRepository;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import com.ecommerce.ecommerce_app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartDTO addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return convertToDTO(savedCart);
    }

    private CartDTO convertToDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .cartItems(cart.getCartItems().stream()
                        .map(item -> CartItemDTO.builder()
                                .product(ProductDTO.builder()
                                        .id(item.getProduct().getId())
                                        .name(item.getProduct().getName())
                                        .price(item.getProduct().getPrice())
                                        .imageUrl(item.getProduct().getImageUrl())
                                        .description(item.getProduct().getDescription())
                                        .categoryName(item.getProduct().getCategoryName())
                                        .itemSize(item.getProduct().getItemSize() != null ? item.getProduct().getItemSize().name() : Size.L.name())
                                        .stock(item.getProduct().getStock())
                                        .build())
                                .quantity(item.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }


    @Override
    public CartDTO getCartByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        return convertToDTO(cart);
    }

    @Override
    public void removeCartItem(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")))
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")))
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));
        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }
}
