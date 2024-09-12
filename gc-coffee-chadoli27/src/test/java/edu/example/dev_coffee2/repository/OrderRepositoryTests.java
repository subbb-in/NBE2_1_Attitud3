package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.entity.Order;
import edu.example.dev_coffee2.entity.OrderItem;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.enums.OrderStatus;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){
        String email = "test@test.com";
        Long productId = 5L;
        int quantity = 2;

        Optional<Order> foundOrder = orderRepository.findByEmail(email);
        Order savedOrder = foundOrder.orElseGet(() -> {
            Order order = Order.builder().email(email).build();
            return orderRepository.save(order);
        });

        Product product = productRepository.findById(productId).get();

        OrderItem orderItem = OrderItem.builder()
                .order(savedOrder)
                .product(product)
                .quantity(quantity)
                .price(product.getPrice()*quantity)
                .build();

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        assertNotNull(savedOrderItem);
    }

    @Test
    public void testGetOrderItems(){
        String email = "test@test.com";

        Optional<List<OrderItem>> foundOrderItems = orderItemRepository.getOrderItems(email);
        assertTrue(foundOrderItems.isPresent());

        List<OrderItem> orderItemList = foundOrderItems.orElse(null);
        assertNotNull(orderItemList);

        orderItemList.forEach(orderItem -> {
            System.out.println("------------");
            System.out.println(orderItem);
            System.out.println(orderItem.getProduct());
        });
    }

    @Test
    public void testGetOrderNOrderItems(){
        String email = "test@test.com";

        Optional<Order> foundOrder = orderRepository.findByEmail(email);
        assertTrue(foundOrder.isPresent());
        Order order = foundOrder.get();
        assertNotNull(order);

        Optional<List<OrderItem>> foundOrderItems = orderItemRepository.getOrderItems(email);
        assertTrue(foundOrderItems.isPresent());
        List<OrderItem> orderItemList = foundOrderItems.orElse(null);
        assertNotNull(orderItemList);

        System.out.println("order : " + order);
        orderItemList.forEach(orderItem -> {
            System.out.println("------------");
            System.out.println(orderItem);
            System.out.println(orderItem.getProduct());
        });
    }

    @Transactional
    @Commit
    @Test
    public void testUpdateOrderItem(){
        Long orderItemId = 1L;
        int quantity = 10;

        Optional<OrderItem> foundOrderItem = orderItemRepository.findById(Math.toIntExact(orderItemId));
        assertNotNull(foundOrderItem);

        OrderItem orderItem = foundOrderItem.get();
        orderItem.changeQuantity(quantity);
        orderItem.changePrice(quantity * foundOrderItem.get().getProduct().getPrice());

        foundOrderItem = orderItemRepository.findById(Math.toIntExact(orderItemId));
        assertEquals(quantity, foundOrderItem.get().getQuantity());
    }

    @Transactional
    @Commit
    @Test
    public void testUpdateOrder(){
        OrderStatus orderStatus = OrderStatus.SHIPPED;
        String email = "test@test.com";

        Optional<Order> foundOrder = orderRepository.findByEmail(email);
        assertNotNull(foundOrder);

        Order order = foundOrder.get();
        order.changeOrderStatus(orderStatus);

        foundOrder = orderRepository.findByEmail(email);
        assertEquals(orderStatus, foundOrder.get().getOrderStatus());
    }


}
