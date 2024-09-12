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

        for(Category category : Category.values()){
            Product product = Product.builder()
                    .productName(category.name())
                    .price(20000)
                    .description(category.name() + "설명")
                    .category(category)
                    .build();

            productRepository.save(product);
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void testRead(){
        Long productId = 3L;  //given

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");
    }

    @Test   //update 테스트 - 트랜젝션 O
    @Commit
    @Transactional
    public void testUpdate(){
        Long productId = 1L;
        String productName = "변경 커피";
        int price = 10000;
        Category category = Category.EthiopiaSidamo;

        Optional<Product> foundProduct = productRepository.findById(productId);
        assertTrue(foundProduct.isPresent(), "Product should be present");

        Product product = foundProduct.get();

        product.changeProductName(productName);
        product.changePrice(price);
        product.changeCategory(category);

        foundProduct = productRepository.findById(productId);

        assertEquals(productName, foundProduct.get().getProductName());
        assertEquals(price, foundProduct.get().getPrice());

    }

    @Test   // delete 테스트 - 트랜잭션 O
    @Transactional
    @Commit
    public void testDelete(){
        Long productId = 4L;
        assertTrue(productRepository.findById(productId).isPresent(), "Product should be present");
        productRepository.deleteById(productId);
        assertFalse(productRepository.findById(productId).isPresent(), "Product should not be present");
    }

    @Test   // 페이징 테스트
    public void testList(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("productId"));
        Page<ProductDTO> products = productRepository.listAll(pageable);

        assertNotNull(products);
        assertEquals(11, products.getTotalElements()); // 전체 게시물 수 100개
        assertEquals(2, products.getTotalPages());     // 총 페이지 수 10개
        assertEquals(0, products.getNumber());          // 현재 페이지 번호 1
        assertEquals(10, products.getSize());           // 한 페이지 게시물 수 10
        assertEquals(10, products.getContent().size()); //         "

        products.getContent().forEach(System.out::println);

    }

}