package edu.example.dev_coffee2.controller;

import edu.example.dev_coffee2.dto.OrderDTO;
import edu.example.dev_coffee2.dto.OrderItemDTO;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.exception.OrderException;
import edu.example.dev_coffee2.exception.ProductException;
import edu.example.dev_coffee2.service.OrderService;
import edu.example.dev_coffee2.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Log4j2
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<List<OrderItemDTO>> add(@RequestBody OrderItemDTO orderItemDTO) {

        Product product = productService.read(orderItemDTO.getProductId()).toEntity();
        if(product == null || !product.getProductName().equals(orderItemDTO.getProductName())) {
            throw ProductException.NOT_FOUND.get();
        }

        if(orderItemDTO.getPrice() != product.getPrice() * orderItemDTO.getQuantity() ){
            throw OrderException.NOT_MATCHED_PRICE.get();
        }

        orderService.add(orderItemDTO);
        return null;
    }

    @GetMapping("/{email}")
    public ResponseEntity<OrderDTO> get(@PathVariable String email) {
        return ResponseEntity.ok(orderService.getOrderNItems(email));
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<?> modify(@RequestBody OrderItemDTO orderItemDTO, @PathVariable("orderItemId") Long orderItemId) {

        try{
            orderService.modify(orderItemDTO);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            throw OrderException.FAIL_MODIFY.get();
        }
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("orderItemId") Long orderItemId) {
        orderService.remove(orderItemId);
        return ResponseEntity.ok(Map.of("message", "Order deleted"));
    }




}
