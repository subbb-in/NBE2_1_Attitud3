package edu.example.dev_coffee2.repository;

import edu.example.dev_coffee2.dto.ProductDTO;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.enums.Category;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@Log4j2
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){

        for (Category category : Category.values()) {
            Product product = Product.builder()
                    .category(category)
                    .productName(category.name())
                    .price(20000)
                    .description( category.name() + " 설명 " ) // 임의의 설명
                    .build();
            Product savedProduct = productRepository.save(product);  // DB에 저장
            assertNotNull(savedProduct);
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void testRead(){
        Long id = 1L;
        Optional<Product> foundProduct = productRepository.findById(id);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        foundProduct.ifPresent(product -> {
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Product Name: " + product.getProductName());
            System.out.println("Product Price: " + product.getPrice());
            System.out.println("Product Description: " + product.getDescription());
        });

    }

    @Test
    @Transactional
    @Commit
    public void testUpdate(){
        Long id = 1L;
        String pname = "아메리카노";
        String pdescription = "변경된 제품 입니다.";
        int price = 2500;

        Optional<Product> foundProduct = productRepository.findById(id);
        assertTrue(foundProduct.isPresent(), "Product should be present");
        Product product = foundProduct.get();
        Category category = Category.EthiopiaSidamo; // 변경할 카테고리 이름 (enum에 선언한 이름내에서만 선택 가능)
        
        product.changeCategory(category);
        product.changeProductName(pname);
        product.changePrice(price);
        product.changeDescription(pdescription);

        foundProduct = productRepository.findById(id);
        assertEquals(pname, foundProduct.get().getProductName());
        assertEquals(price, foundProduct.get().getPrice());

    }

    @Test
    public void testDelete(){
        Long id = 1L;

        assertTrue(productRepository.findById(id).isPresent(),
                "Product should be present");

        productRepository.deleteById(id);

        assertFalse(productRepository.findById(id).isPresent(),
                "Product should be present");
    }


    @Test   // 페이징 테스트
    public void testPaging(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("productId"));
        Page<ProductDTO> productDTO = productRepository.listAll(pageable);

        assertNotNull(productDTO);
        assertEquals(4, productDTO.getTotalElements()); // 전체 게시물 수 100개
        assertEquals(1, productDTO.getTotalPages());     // 총 페이지 수 10개
        assertEquals(0, productDTO.getNumber());          // 현재 페이지 번호 1
        assertEquals(10, productDTO.getSize());           // 한 페이지 게시물 수 10
        assertEquals(4, productDTO.getContent().size()); //         "

        productDTO.getContent().forEach(System.out::println);

    }

}