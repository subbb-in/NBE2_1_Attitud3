package edu.example.dev_coffee2.entity;

import edu.example.dev_coffee2.dto.OrderItemDTO;
import edu.example.dev_coffee2.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders", indexes = @Index(columnList = "email"))
@EntityListeners(value = AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Email
    private String email;

    private String address;
    private String postcode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderItem> orderItems;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @CreatedDate
    private LocalDateTime regDate;
    @LastModifiedDate
    private LocalDateTime modDate;

    public void changeAddress(String address){
        this.address = address;
    }
    public void changePostCode(String postcode){
        this.postcode = postcode;
    }
    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }



}
