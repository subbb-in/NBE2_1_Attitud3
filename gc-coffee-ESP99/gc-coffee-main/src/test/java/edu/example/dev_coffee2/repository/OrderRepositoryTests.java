package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.dto.OrderDTO;
import edu.example.dev_coffee2.entity.Order;
import edu.example.dev_coffee2.entity.OrderItem;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.enums.OrderStatus;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        Long id = 2L;
        String address = "배달의 민족";
        String postcode = "449492";
        OrderStatus orderStatus = OrderStatus.CANCELLED;
        String email = "ffff@fff.com";
        int quantity = 3;

        Optional<Order> foundorder = orderRepository.findByEmail(email);
            Order savedOrder = foundorder.orElseGet(() -> { // 주문자 정보가 없다면 만들어줌
            Order order = Order.builder().email(email)
                    .address(address).postcode(postcode)
                    .orderStatus(orderStatus)
                    .build();
            // order > orderId도 들어있다.
            return orderRepository.save(order);
        });

        // productId로 Product 조회하여 해당 id의 모든 정보를 foundProduct에 저장
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // OrderItem 객체 생성 및 Order, Produt 객체 정보들을 저장
        OrderItem orderItem = OrderItem.builder()
                .quantity(quantity)
                .product(foundProduct)  
                .price(foundProduct.getPrice() * quantity)
                .category(foundProduct.getCategory())
                .order(savedOrder)
                .build();

        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        assertNotNull(savedOrderItem);
        assertEquals(2, savedOrderItem.getOrderItemId());
        assertEquals(id, savedOrderItem.getProduct().getProductId());
    }
    // 조회 (orderItemRepository @Query 문 확인
    @Test
    // could not initialize proxy - no Session 발생해서  @Transactional 추가
    @Transactional
    public void testGetOrderItems(){
        String email = "hong@example.com";

        Optional<List<OrderItem>> foundOrderItems = orderItemRepository.getOrderByEmail(email);
        assertTrue(foundOrderItems.isPresent(), "foundOrderItems should be present");

        List<OrderItem> orderItemsList = foundOrderItems.orElse(null); // orderItem을 다시 가져와서 리스트에 담음
        assertNotNull(orderItemsList);

        orderItemsList.forEach( orderItem -> {
            System.out.println("-------------------");
            System.out.println(orderItem.getOrder());
            System.out.println(orderItem.getProduct().getProductId());
            System.out.println(orderItem.getProduct().getCategory());
            System.out.println(orderItem.getPrice()); // 수량 * 상품 가격
            
        });
    }

}
