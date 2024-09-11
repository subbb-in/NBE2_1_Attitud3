package edu.example.dev_coffee2.repository;
import edu.example.dev_coffee2.dto.ProductDTO;
import edu.example.dev_coffee2.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(" SELECT p FROM Product p WHERE p.productId = :productId ")
    Optional<ProductDTO> getProductDTO(@Param("productId") Long id);

    @Query("SELECT p FROM Product p ORDER BY p.productId ")
    Page<ProductDTO> listAll(Pageable pageable);

}
