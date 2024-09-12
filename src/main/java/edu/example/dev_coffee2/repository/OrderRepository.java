package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.dto.OrderDTO;
import edu.example.dev_coffee2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByEmail(String email);

    @Query("SELECT o FROM Order o JOIN FETCH OrderItem WHERE o.email = :email")
    Optional<Order> findByEmailWithItems(@Param("email") String email);
}

//    Optional<OrderDTO> findByDTOEmail(String email);


