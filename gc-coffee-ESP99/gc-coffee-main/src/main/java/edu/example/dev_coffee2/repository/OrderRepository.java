package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.dto.OrderDTO;
import edu.example.dev_coffee2.dto.OrderItemDTO;
import edu.example.dev_coffee2.dto.ProductDTO;
import edu.example.dev_coffee2.entity.Order;
import edu.example.dev_coffee2.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByEmail(String email);

    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.orderItems oi " +
            "JOIN FETCH oi.product p " +
            "WHERE o.email = :email")
    Optional<List<Order>> getOrderByEmail(String email);

    @Query("SELECT o FROM Order o ORDER BY o.orderId ")
    Page<OrderDTO> listAll(Pageable pageable);
}
