package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.model.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    OrderDTO placeOrder(OrderDTO orderDTO);

    OrderDTO getOrder(Long id);

    List<OrderDTO> getAllOrdersByUser(Long userId);

    void cancelOrder(Long id);

    OrderDTO getOrderDetails(Long id);
}
