package edu.example.dev_coffee2.controller;

import edu.example.dev_coffee2.dto.OrderDTO;
import edu.example.dev_coffee2.dto.OrderItemDTO;
import edu.example.dev_coffee2.dto.PageRequestDTO;
import edu.example.dev_coffee2.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Log4j2
public class OrderController {
    private final OrderService orderService;
    
    // 주문 추가
    @PostMapping
    public ResponseEntity<List<OrderItemDTO>> addOrder(@RequestBody OrderItemDTO orderItemDTO){
        log.info("----- addOrder()");

        orderService.add(orderItemDTO);
        return ResponseEntity.ok(orderService.getAllItems(orderItemDTO.getEmail()));
    }


    // 한명의 회원 주문 전체 조회
    @GetMapping("/{email}")
    public ResponseEntity<List<OrderDTO>> showOrder(@PathVariable("email") String email){
        log.info("Received email: " + email); // 이메일 확인
        List<OrderDTO> orderDTOList = orderService.getAllOrders(email);
        log.info("Items to return: " + orderDTOList);
        return ResponseEntity.ok(orderService.getAllOrders(email));
    }

    // 수정
    @PutMapping("/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> modify(@RequestBody OrderItemDTO orderItemDTO,
                                                     @PathVariable("orderId") Long orderId){
        log.info("--- modify()");
        orderService.modify(orderItemDTO, orderId);

        return ResponseEntity.ok(orderService.getAllItems(orderItemDTO.getEmail()));
    }

    // Order 삭제
    @DeleteMapping("/{orderId}")
    public void removeOrder(@PathVariable Long orderId){
        log.info("--- remove()");

        orderService.deleteOrder(orderId);

    }

    // OrderItems 삭제
    @DeleteMapping("orderItem/{orderItemId}")
    public void remove(@PathVariable Long orderItemId){
        log.info("--- remove()");

        orderService.deleteOrderItem(orderItemId);

    }

}
