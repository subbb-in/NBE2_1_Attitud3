package edu.example.dev_coffee2.entity;

import edu.example.dev_coffee2.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@EntityListeners(value = AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private int price;
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }
}
