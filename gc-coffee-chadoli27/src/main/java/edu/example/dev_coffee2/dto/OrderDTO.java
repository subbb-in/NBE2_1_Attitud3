package edu.example.dev_coffee2.dto;

import edu.example.dev_coffee2.entity.Order;
import edu.example.dev_coffee2.entity.OrderItem;
import edu.example.dev_coffee2.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDTO {
    private Order order;
    private List<OrderItem> orderItems;

    public OrderDTO(Order order, List<OrderItem> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
}
