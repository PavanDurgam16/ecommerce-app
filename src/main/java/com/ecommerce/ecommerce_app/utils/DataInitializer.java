package com.ecommerce.ecommerce_app.utils;

import com.ecommerce.ecommerce_app.enums.RoleType;
import com.ecommerce.ecommerce_app.enums.Size;
import com.ecommerce.ecommerce_app.model.dto.ProductDTO;
import com.ecommerce.ecommerce_app.model.entity.Product;
import com.ecommerce.ecommerce_app.model.entity.Role;
import com.ecommerce.ecommerce_app.model.entity.User;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import com.ecommerce.ecommerce_app.repository.RoleRepository;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import com.ecommerce.ecommerce_app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Override
    public void run(String... args) throws Exception {

        /*
        try {
            productService.fetchAndStoreProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Role adminRole = new Role(1L, RoleType.ADMIN);

        Role userRole = new Role(2L, RoleType.CUSTOMER);

        roleRepository.saveAll(Set.of(adminRole, userRole));

        // Inserting users
        User pavan = User.builder()
                .id(1L)
                .email("pavan16@gmail.com")
                .username("pavan16")
                .firstName("pavan")
                .lastName("durgam")
                .phoneNumber("987456210")
                .password(passwordEncoder.encode("pavan"))
                .roles(Set.of(userRole))
                .build();

        User admin = User.builder()
                .id(2L)
                .email("admin@gmail.com")
                .username("admin")
                .firstName("admin")
                .lastName("user")
                .phoneNumber("9133456780")
                .password(passwordEncoder.encode("admin"))
                .roles(Set.of(adminRole))
                .build();

        userRepository.saveAll(Set.of(pavan, admin));

        pavan.setRoles(Set.of(userRole));
        admin.setRoles(Set.of(adminRole));

        userRepository.save(pavan);
        userRepository.save(admin);*/


       /* try {
            // Create a sample product DTO
            // Product 1: Handbag (from "Elegant Designs")
            Path handbagImagePath = Path.of("src/main/resources/static/handbag.png");
            Path watchImagePath = Path.of("src/main/resources/static/watch.png");
            Path headphonesImagePath = Path.of("src/main/resources/static/headphones.png");
            Path cosmeticsImagePath = Path.of("src/main/resources/static/cosmetic.png");

            // Create MultipartFile instances using the utility method
            MultipartFile handbagImage = MultipartFileUtil.createMultipartFile("handbag", "handbag.png", "image/png", handbagImagePath);
            MultipartFile watchImage = MultipartFileUtil.createMultipartFile("watch", "watch.png", "image/png", watchImagePath);
            MultipartFile headphonesImage = MultipartFileUtil.createMultipartFile("headphones", "headphones.png", "image/png", headphonesImagePath);
            MultipartFile cosmeticsImage = MultipartFileUtil.createMultipartFile("cosmetics", "cosmetic.png", "image/png", cosmeticsImagePath);


            ProductDTO handbag = ProductDTO.builder()
                    .name("Classic Leather Handbag")
                    .description("A stylish handbag crafted from premium leather, perfect for everyday use.")
                    .price(new BigDecimal("99.99"))
                    .itemSize(Size.L.name())
                    .stock(50)
                    .categoryName("Accessories")
                    .imageUrl("https://example.com/images/handbag.png")
                    .build();

            productService.createProduct(handbag, handbagImage);

            // Product 2: Watch (from "Timely Inc.")

            ProductDTO watch = ProductDTO.builder()
                    .name("Chronograph Watch")
                    .description("A modern chronograph watch with a sleek stainless steel band and water resistance.")
                    .price(new BigDecimal("199.99"))
                    .itemSize(Size.L.name())
                    .stock(30)
                    .categoryName("Watches")
                    .imageUrl("https://example.com/images/watch.png")
                    .build();

            productService.createProduct(watch, watchImage);

            // Product 3: Music Headphones (from "SoundBeats")

            ProductDTO headphones = ProductDTO.builder()
                    .name("Wireless Noise-Cancelling Headphones")
                    .description("Enjoy high-quality sound with these wireless noise-cancelling headphones, perfect for music lovers.")
                    .price(new BigDecimal("149.99"))
                    .itemSize(Size.XL.name())
                    .stock(70)
                    .categoryName("Electronics")
                    .imageUrl("https://example.com/images/headphones.png")
                    .build();

            productService.createProduct(headphones, headphonesImage);

            // Product 4: Cosmetic (from "Glow Beauty")

            ProductDTO cosmetic = ProductDTO.builder()
                    .name("Luxury Matte Lipstick")
                    .description("A long-lasting matte lipstick available in multiple shades for a bold look.")
                    .price(new BigDecimal("29.99"))
                    .itemSize(Size.M.name())
                    .stock(120)
                    .categoryName("Cosmetics")
                    .imageUrl("https://example.com/images/cosmetic.png")
                    .build();

            productService.createProduct(cosmetic, cosmeticsImage);

            System.out.println("Products created successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
        }*/
    }
}
