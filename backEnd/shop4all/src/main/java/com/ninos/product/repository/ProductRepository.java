package com.ninos.product.repository;

import com.ninos.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByCategoryNameAndBrand(String category, String brand);
    List<Product> findProductsByCategoryName(String category);
    List<Product> findProductsByBrandAndName(String brand, String name);
    List<Product> findProductsByBrand(String brand);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findProductsByName(String name);

    boolean existsByNameAndBrand(String name, String brand);


}
