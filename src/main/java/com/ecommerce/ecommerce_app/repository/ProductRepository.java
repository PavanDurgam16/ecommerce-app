package com.ecommerce.ecommerce_app.repository;

import com.ecommerce.ecommerce_app.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.imageUrl FROM Product p WHERE p.id = :id")
    String getImageUrlById(@Param("id") Long id);

}