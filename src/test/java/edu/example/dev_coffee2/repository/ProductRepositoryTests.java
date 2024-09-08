//package edu.example.dev_coffee2.repository;
//
//import edu.example.dev_coffee2.entity.Product;
//import edu.example.dev_coffee2.enums.Category;
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@Log4j2
//public class ProductRepositoryTests {
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Test
//    public void testInsert(){
//
//        Category category = Category.category1;
//
//        Product product = Product.builder()
//                .description("중국 공장")
//                .price(500)
//                .category(category)
//                .productName("이름모를곳에서 탄생").build();
//
//        Product savedProduct = productRepository.save(product);
//        assertNotNull(savedProduct);
//    }
//}