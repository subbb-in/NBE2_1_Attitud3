package edu.example.dev_coffee2.service;

import edu.example.dev_coffee2.dto.OrderDTO;
import edu.example.dev_coffee2.dto.OrderItemDTO;
import edu.example.dev_coffee2.dto.PageRequestDTO;
import edu.example.dev_coffee2.entity.Order;
import edu.example.dev_coffee2.entity.OrderItem;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.repository.OrderItemRepository;
import edu.example.dev_coffee2.repository.OrderRepository;
import edu.example.dev_coffee2.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    // 등록
    public void add(OrderItemDTO orderItemDTO){
        // email을 기준으로 조회
        Optional<Order> foundorder = orderRepository.findByEmail(orderItemDTO.getEmail());

        Order savedOrder = foundorder.orElseGet(() -> { // 주문자 정보가 없다면 만들어줌
            Order order = Order.builder().email(orderItemDTO.getEmail())
                    .address(orderItemDTO.getAddress()).postcode(orderItemDTO.getPostcode())
                    .orderStatus(orderItemDTO.getOrderStatus())
                    .build();
            // order > orderId도 들어있다.
            return orderRepository.save(order);
        });

        // productId로 Product 조회하여 해당 id의 모든 정보를 foundProduct에 저장
        Product foundProduct = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + orderItemDTO.getProductId()));


        // OrderItem 객체 생성 및 Order, Produt 객체 정보들을 저장
        OrderItem orderItem = OrderItem.builder()
                .quantity(orderItemDTO.getQuantity())
                .product(foundProduct)
                .price(foundProduct.getPrice() * orderItemDTO.getQuantity())
                .category(foundProduct.getCategory())
                .order(savedOrder)
                .build();

        try {
            orderItemRepository.save(orderItem); //장바구니에 담기
        } catch(Exception e) {
            log.error("--- " + e.getMessage()); //에러 로그로 발생 예외의 메시지를 기록하고
            // 예외를 던져야 하나 생략
        }
    }

    // 각 회원(이메일) 주문 목록 조회
    public List<OrderItemDTO> getAllItems(String email){
        // 주문 목록이 없다면 null (entity)
        List<OrderItem> orderItemList = orderItemRepository.getOrderByEmail(email).orElse(null);

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        if(orderItemList.isEmpty()){ // DB에 주문 목록이 비어 있으면
            return orderItemDTOList; // 비어 있는 orderItemDTOList를 반환
        }

        orderItemList.forEach(orderItem -> { // 비어 있지 않으면
            // entity >>> DTO로 변환하여 orderItemDTOList 저장하여 반환
            orderItemDTOList.add(OrderItemDTO.builder()
                    // Order 부분
                    .email(orderItem.getOrder().getEmail())
                    .address(orderItem.getOrder().getAddress())
                    .postcode(orderItem.getOrder().getPostcode())
                    .productId(orderItem.getProduct().getProductId())
                    .category(orderItem.getProduct().getCategory())
                            .orderStatus(orderItem.getOrder().getOrderStatus())
                    .price(orderItem.getPrice())
                    .quantity(orderItem.getQuantity())
                    .build()
            );
        });
        log.info("OrderItemDTO List: " + orderItemDTOList);
        return orderItemDTOList;

    }

    // 각 회원(이메일) 주문 목록 조회
    public List<OrderDTO> getAllOrders(String email){
        // 주문 목록이 없다면 null (entity)
        List<Order> orderList = orderRepository.getOrderByEmail(email).orElse(null);
        System.out.println("--- getOrderByEmailOptional" + orderList);

        // OrderItem 리스트 추출
        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        List<OrderDTO> orderDTOList = new ArrayList<>();

        if (orderList.isEmpty()) {
            // 주문이 비어있는 경우 비어있는 리스트 반환
            return orderDTOList;
        }

        // 각 주문별로 처리
        orderList.forEach(order -> {

            order.getOrderItems().forEach(orderItem -> {
                OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                        .productId(orderItem.getProduct().getProductId())
                        .category(orderItem.getProduct().getCategory())
                        .price(orderItem.getPrice())
                        .quantity(orderItem.getQuantity())
                        .build();
                orderItemDTOList.add(orderItemDTO);
            });

            // OrderDTO 생성 및 리스트에 추가
            OrderDTO orderDTO = OrderDTO.builder()
                    .email(order.getEmail())
                    .address(order.getAddress())
                    .postcode(order.getPostcode())
                    .orderItems(orderItemDTOList)  // OrderItem 리스트 추가
                    .build();

            orderDTOList.add(orderDTO);
        });

        // 로그로 확인
        System.out.println("--- OrderDTO List: " + orderDTOList);
        return orderDTOList;

    }

    // 수정
    public OrderItemDTO modify(OrderItemDTO orderItemDTO, Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        Order modifyOrder = order.orElseThrow(() ->
                new EntityNotFoundException("Order " + orderId + "NOT FOUND")
        );
        // Order 부분
        modifyOrder.changeAddress(orderItemDTO.getAddress());
        modifyOrder.changePostCode(orderItemDTO.getPostcode());
        modifyOrder.changeOrderStatus(orderItemDTO.getOrderStatus());
        
        // OrderItem 부분
        modifyOrder.getOrderItems().forEach(orderItem -> {
            orderItem.changeQuantity(orderItemDTO.getQuantity()); // 클라이언트가 보낸 수량을 엔티티에 저장
            orderItem.setPrice(orderItem.getPrice() * orderItem.getQuantity()); // 상품 가격 * 수정된 수량으로 가격 수정
            orderItem.changeCategory(orderItemDTO.getCategory()); // 클라이언트가 보낸 카테고리를 엔티티에 저장
        });

        orderRepository.save(modifyOrder);
        return orderItemDTO;
    }

    // Order 삭제
    public void deleteOrder(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        Order deleteOrder = order.orElseThrow(() ->
                new EntityNotFoundException("OrderId " + orderId + "NOT FOUND")
        );

        orderRepository.delete(deleteOrder);
    }

    // OrderItems 삭제
    public void deleteOrderItem(Long orderItemId){
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderItemId);
        OrderItem deleteOrder = orderItem.orElseThrow(() ->
                new EntityNotFoundException("OrderId " + orderItemId + "NOT FOUND")
        );

        orderItemRepository.delete(deleteOrder);
    }

}
