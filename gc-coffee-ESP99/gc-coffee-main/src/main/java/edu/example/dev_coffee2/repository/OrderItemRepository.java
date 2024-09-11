package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.dto.OrderItemDTO;
import edu.example.dev_coffee2.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi " +
            "JOIN FETCH oi.product p " +
            "JOIN FETCH oi.order o " +
            "WHERE o.email = :email " +
            "ORDER BY oi.orderItemId DESC")
    Optional<List<OrderItem>> getOrderByEmail(@Param("email") String email);
}
