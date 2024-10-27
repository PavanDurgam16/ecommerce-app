package com.ecommerce.ecommerce_app.model.entity;

import com.ecommerce.ecommerce_app.enums.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_size")
    private Size itemSize;


    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String categoryName;


}
