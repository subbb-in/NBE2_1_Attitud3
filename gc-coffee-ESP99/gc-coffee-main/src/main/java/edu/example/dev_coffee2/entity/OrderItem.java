package edu.example.dev_coffee2.entity;

import edu.example.dev_coffee2.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"product", "order"})
@Table(name = "orderItems")
public class OrderItem  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private Category category;

    private int quantity;

    private int price;  // 총 가격

    public void changeQuantity(int quantity){
        this.quantity = quantity;
    }
    public void changeCategory(Category category){
        this.category = category;
    }


}
