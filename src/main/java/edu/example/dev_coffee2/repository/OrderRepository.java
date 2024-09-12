package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.dto.OrderDTO;
import edu.example.dev_coffee2.entity.Order;
import edu.example.dev_coffee2.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByEmail(String email);

    @Query("SELECT o FROM Order o JOIN FETCH OrderItem WHERE o.email = :email")
    Optional<Order> findByEmailWithItems(@Param("email") String email);

    List<Order> findByRegDateBetweenAndOrderStatusNot(LocalDateTime start, LocalDateTime end, OrderStatus status);
}

//    Optional<OrderDTO> findByDTOEmail(String email);


