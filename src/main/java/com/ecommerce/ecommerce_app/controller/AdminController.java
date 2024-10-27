package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.model.dto.ProductDTO;
import com.ecommerce.ecommerce_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/admin/products")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDTO> createProduct(@RequestPart("product") ProductDTO productDTO,
                                                    @RequestPart("image") MultipartFile imageFile) throws IOException {
        ProductDTO createdProduct = productService.createProduct(productDTO, imageFile);
        return ResponseEntity.ok(createdProduct);
    }


    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductDTO productDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO, imageFile);
        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
