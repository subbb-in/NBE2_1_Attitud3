package edu.example.dev_coffee2.repository;


import edu.example.dev_coffee2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;




public interface ProductRepository extends JpaRepository<Product, Long> {

}
