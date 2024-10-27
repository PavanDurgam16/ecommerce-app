package com.ecommerce.ecommerce_app.service.impl;

import com.amazonaws.util.StringUtils;
import com.ecommerce.ecommerce_app.enums.FileType;
import com.ecommerce.ecommerce_app.enums.Size;
import com.ecommerce.ecommerce_app.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_app.model.dto.ProductDTO;
import com.ecommerce.ecommerce_app.model.entity.Product;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import com.ecommerce.ecommerce_app.service.AwsService;
import com.ecommerce.ecommerce_app.service.ProductService;
import com.ecommerce.ecommerce_app.utils.FileMultipart;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final AwsService awsService;

    private final RestTemplate restTemplate;

    private static final String[] RANDOM_CATEGORIES = { "electronics", "clothing", "furniture", "food", "accessories" };
    private static final String[] RANDOM_NAMES = { "Test Product A", "Sample Product B", "Random Gadget C", "Item X", "Gizmo Y" };
    private static final String DEFAULT_DESCRIPTION = "This is a randomly generated description.";

    private static final Random random = new Random();


    private ProductDTO convertToDTO(Product product) {
//        String size = (!StringUtils.isNullOrEmpty(product.getItemSize().name())) ? product.getItemSize().name() : Size.L.name();

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .itemSize(product.getItemSize() != null ? product.getItemSize().name() : Size.L.name())
                .imageUrl(product.getImageUrl())
                .stock(product.getStock())
                .build();
    }

    // Add a new product with image
    @Override
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile imageFile) throws IOException {

        Size size = (productDTO.getItemSize() != null) ? Size.valueOf(productDTO.getItemSize()) : Size.L; // Default Size
        String imageURL = awsService.uploadFile(imageFile);
        Product product = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .itemSize(size)
                .imageUrl(StringUtils.isNullOrEmpty(imageURL) ? "" : imageURL)
                .stock(productDTO.getStock())
                .categoryName(productDTO.getCategoryName())
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setItemSize(Size.valueOf(productDTO.getItemSize()));
        product.setStock(productDTO.getStock());
        product.setCategoryName(productDTO.getCategoryName());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageURL = awsService.uploadFile(imageFile);
            product.setImageUrl(imageURL);
        } else {
            product.setImageUrl(product.getImageUrl());
        }

        Product updatedProduct = productRepository.save(product);
        return convertToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product is not found with id : " + id));
        productRepository.deleteById(product.getId());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ProductApiResponse {
        private Long id;
        private String title;
        private BigDecimal price;
        private String category;
        private String description;
        private String image;

    }


    @Override
    // Method to fetch and store products
    public void fetchAndStoreProducts() throws IOException {
        String apiUrl = "https://fakestoreapi.com/products";
        ProductApiResponse[] apiProducts = restTemplate.getForObject(apiUrl, ProductApiResponse[].class);

        if (apiProducts != null) {
            for (ProductApiResponse apiProduct : apiProducts) {
                ProductDTO productDTO = new ProductDTO();
                String title = (apiProduct.getTitle() != null && !apiProduct.getTitle().isEmpty())
                        ? apiProduct.getTitle()
                        : getRandomName();
                productDTO.setName(title);

                // Assign random description if null
                String description = (apiProduct.getDescription() != null && !apiProduct.getDescription().isEmpty())
                        ? apiProduct.getDescription()
                        : DEFAULT_DESCRIPTION;
                productDTO.setDescription(description);

                String itemSize = Size.L.name();
                productDTO.setItemSize(itemSize);

                // Assign random category if null
                String category = (apiProduct.getCategory() != null && !apiProduct.getCategory().isEmpty())
                        ? apiProduct.getCategory()
                        : getRandomCategory();
                productDTO.setCategoryName(category);

                // Assign random category if null
                Integer stock = random.nextInt(100) + 1;;
                productDTO.setStock(stock);


                // Set price (leave as null or handle zero-price products if needed)
                productDTO.setPrice(apiProduct.getPrice() != null ? apiProduct.getPrice() : BigDecimal.valueOf(10 + (500 - 10) * random.nextDouble()));



                // Check if the image URL is null, if so, handle accordingly
                if (apiProduct.getImage() != null && !apiProduct.getImage().isEmpty()) {
                    String s3ImageUrl = uploadImageToS3(apiProduct.getImage());
                    productDTO.setImageUrl(s3ImageUrl);
                } else {
                    productDTO.setImageUrl("https://ecommerce-app-s3-be.s3.amazonaws.com/default-gradient-image.jpg");
                }

                // Save the product to the repository
                productRepository.save(mapToProductEntity(productDTO));
            }
        }
    }

    private Product mapToProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategoryName(productDTO.getCategoryName());
        product.setImageUrl(productDTO.getImageUrl());
        return product;
    }

    @Override
    public ProductDTO createProductFromApi(ProductDTO productDTO) throws IOException {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setItemSize(Size.XL);

        String s3ImageUrl = uploadImageToS3(productDTO.getImageUrl());
        product.setImageUrl(s3ImageUrl);

        productRepository.save(product);

        return productDTO;
    }

    private String uploadImageToS3(String imageUrl) throws IOException {
        // Create a URL object from the image URL
        URL url = new URL(imageUrl);

        // Download the image
        try (InputStream inputStream = url.openStream()) {
            // Convert InputStream to byte array
            byte[] content = inputStream.readAllBytes();

            // Create a MultipartFile using the FileMultipart class
            MultipartFile multipartFile = new FileMultipart(
                    "file", // name
                    imageUrl.substring(imageUrl.lastIndexOf('/') + 1), // originalFilename
                    FileType.fromFilename(imageUrl).toString(), // contentType
                    content // file content
            );

            // Upload the MultipartFile to S3 using your existing uploadFile method
            String s3Url = awsService.uploadFile(multipartFile);
            return s3Url; // Return the S3 URL
        } catch (IOException e) {
            throw new IOException("Failed to upload image to S3", e);
        }
    }


    private String getRandomName() {
        int randomIndex = random.nextInt(RANDOM_NAMES.length);
        return RANDOM_NAMES[randomIndex];
    }

    // Generate a random category
    private String getRandomCategory() {
        int randomIndex = random.nextInt(RANDOM_CATEGORIES.length);
        return RANDOM_CATEGORIES[randomIndex];
    }


}
