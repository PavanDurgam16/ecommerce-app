package com.ecommerce.ecommerce_app.service.impl;

import com.ecommerce.ecommerce_app.enums.OrderStatus;
import com.ecommerce.ecommerce_app.enums.Size;
import com.ecommerce.ecommerce_app.model.dto.OrderDTO;
import com.ecommerce.ecommerce_app.model.dto.OrderItemDTO;
import com.ecommerce.ecommerce_app.model.dto.ProductDTO;
import com.ecommerce.ecommerce_app.model.entity.Order;
import com.ecommerce.ecommerce_app.model.entity.OrderItem;
import com.ecommerce.ecommerce_app.model.entity.Product;
import com.ecommerce.ecommerce_app.model.entity.User;
import com.ecommerce.ecommerce_app.repository.OrderRepository;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import com.ecommerce.ecommerce_app.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    @Override
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        if (orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty()) {
            throw new RuntimeException("Order items cannot be null or empty");
        }
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);

        List<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(orderItemDTO -> {
            Product product = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(orderItemDTO.getQuantity())));
            orderItem.setOrder(order);

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalAmount(orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    private OrderDTO convertToDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .orderItems(order.getOrderItems().stream().map(item -> {

                    var product = ProductDTO.builder()
                            .id(item.getProduct().getId())
                            .name(item.getProduct().getName())
                            .stock(item.getProduct().getStock())
                            .imageUrl(item.getProduct().getImageUrl())
                            .categoryName(item.getProduct().getCategoryName())
                            .itemSize(item.getProduct().getItemSize() != null ? item.getProduct().getItemSize().name() : Size.XL.name())
                            .build();
                    return OrderItemDTO.builder()
                            .productId(item.getProduct().getId())
                            .product(product)
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build();
                }).collect(Collectors.toList()))
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .build();
    }
    @Override
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public OrderDTO getOrderDetails(Long id) {
        return null;
    }
}
