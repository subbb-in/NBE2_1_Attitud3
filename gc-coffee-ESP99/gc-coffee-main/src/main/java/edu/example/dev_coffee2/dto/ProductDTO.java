package edu.example.dev_coffee2.dto;

import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class ProductDTO {
    private Long productId;
    @NotEmpty
    private String productName;
    @Min(0)
    private int price;

    private String description;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public ProductDTO(Product product){
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.category = product.getCategory();
        this.createdAt = product.getCreatedAt();
        this.updateAt = product.getUpdatedAt();

    }

    public Product toEntity(){
        return  Product.builder()
                .productName(productName)
                .price(price)
                .description(description)
                .category(category)
                .build();
    }
}
