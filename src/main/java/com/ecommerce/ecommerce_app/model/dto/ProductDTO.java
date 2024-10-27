package com.ecommerce.ecommerce_app.model.dto;

import com.ecommerce.ecommerce_app.enums.Size;
import com.ecommerce.ecommerce_app.model.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    private String itemSize;

    private String imageUrl;

    @NotNull(message = "Stock is required")
    private int stock;

    @NotNull(message = "Category name is required")
    private String categoryName;
}