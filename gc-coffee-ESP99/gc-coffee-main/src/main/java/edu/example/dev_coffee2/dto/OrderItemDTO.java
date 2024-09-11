package edu.example.dev_coffee2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.example.dev_coffee2.entity.OrderItem;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.enums.Category;
import edu.example.dev_coffee2.enums.OrderStatus;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {
    private Long productId;
    private Long orderItemId;

    private String address;
    private String postcode;
    @Email
    private String email;
    OrderStatus orderStatus;
    private Category category;
    private int price;
    private int quantity;


}
