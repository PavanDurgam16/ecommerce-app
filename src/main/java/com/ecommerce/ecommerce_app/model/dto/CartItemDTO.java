package com.ecommerce.ecommerce_app.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {

    @NotNull(message = "Product ID is required")
    private ProductDTO product;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
