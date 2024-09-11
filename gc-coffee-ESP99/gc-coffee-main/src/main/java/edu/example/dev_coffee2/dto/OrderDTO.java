package edu.example.dev_coffee2.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.example.dev_coffee2.entity.Order;
import edu.example.dev_coffee2.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private Long orderId;

    @Email
    private String email;

    @NotEmpty
    private String address;
    @NotEmpty
    private String postcode;
    private OrderStatus orderStatus;
    private List<OrderItemDTO> orderItems;

}
