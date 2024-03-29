package com.micheck12.back.product.repository;

import com.micheck12.back.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images WHERE p.id = :id")
    Optional<Product> findById(Long id);

    @Modifying
    @Query("UPDATE Product p SET p.hit = p.hit + 1 WHERE p.id = :id")
    void increaseHitById(Long id);

}
