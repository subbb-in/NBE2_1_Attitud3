package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("select o from OrderItem o join fetch o.product where o.order.email = :email order by o.orderItemId")
    Optional<List<OrderItem>> getOrderItems(@Param("email") String email);

    @Query("select o from OrderItem o join fetch o.product where o.order.orderId = :orderId")
    Optional<List<OrderItem>> getOrderItems(@Param("orderId") Long orderId);

}
