package com.ecommerce.ecommerce_app.repository;

import com.ecommerce.ecommerce_app.model.entity.Cart;
import com.ecommerce.ecommerce_app.model.entity.Order;
import com.ecommerce.ecommerce_app.model.entity.User;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Order> findByUserId(Long userId);

    Optional<Cart> findByUser(User user);


}