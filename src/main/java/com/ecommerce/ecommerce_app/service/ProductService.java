package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.model.dto.ProductDTO;
import com.ecommerce.ecommerce_app.model.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, MultipartFile imageFile) throws IOException;

    ProductDTO getProduct(Long id);

    List<ProductDTO> getAllProducts();

    ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile imageFile) throws IOException;

    void deleteProduct(Long id);

    ProductDTO createProductFromApi(ProductDTO productDTO) throws IOException;

    void fetchAndStoreProducts() throws IOException;
}
