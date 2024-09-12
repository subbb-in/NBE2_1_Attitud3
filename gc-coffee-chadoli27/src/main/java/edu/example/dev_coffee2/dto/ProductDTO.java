package edu.example.dev_coffee2.dto;

import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.category = product.getCategory();

    }

    public Product toEntity(){
        Product product = Product.builder().productName(productName)
                .price(price)
                .description(description)
                .category(category)
                .build();

        return product;
    }


}
