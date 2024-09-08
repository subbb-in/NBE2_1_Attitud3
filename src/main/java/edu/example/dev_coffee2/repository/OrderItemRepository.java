package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
