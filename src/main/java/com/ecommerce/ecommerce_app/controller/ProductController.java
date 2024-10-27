package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.model.dto.OrderDTO;
import com.ecommerce.ecommerce_app.model.dto.ProductDTO;
import com.ecommerce.ecommerce_app.service.OrderService;
import com.ecommerce.ecommerce_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final OrderService orderService;

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        ProductDTO product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

//    // Place an order
//    @PostMapping("/orders")
//    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
//        OrderDTO createdOrder = orderService.placeOrder(orderDTO);
//        return ResponseEntity.ok(createdOrder);
//    }
//
//    // getOrderDetails with order id
//    @GetMapping("/orders/{id}")
//    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable Long id) {
//        OrderDTO orderDetails = orderService.getOrder(id);
//        return ResponseEntity.ok(orderDetails);
//    }
}
