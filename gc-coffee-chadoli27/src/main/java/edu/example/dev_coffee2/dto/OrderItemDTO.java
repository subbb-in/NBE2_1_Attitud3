package edu.example.dev_coffee2.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDTO {
    private Long orderItemId;
    private int quantity;
    private int price;
    private String productName;
    private Long productId;
    private String email;

    private String address;
    private String postcode;

}
