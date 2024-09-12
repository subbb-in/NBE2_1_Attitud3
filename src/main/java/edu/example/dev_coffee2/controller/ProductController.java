package edu.example.dev_coffee2.controller;

import edu.example.dev_coffee2.dto.PageRequestDTO;
import edu.example.dev_coffee2.dto.ProductDTO;
import edu.example.dev_coffee2.exception.ProductException;
import edu.example.dev_coffee2.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Log4j2
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> register(@Validated @RequestBody ProductDTO productDTO){

        return ResponseEntity.ok(productService.register(productDTO));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> read(@PathVariable("productId") Long productId) {

        return ResponseEntity.ok(productService.read(productId));
    }

    @PutMapping("/{productId}")               //수정
    public ResponseEntity<ProductDTO> modify(@Validated @RequestBody ProductDTO productDTO, @PathVariable("productId") Long productId) {

        if(!productId.equals(productDTO.getProductId())){   //pno가 일치하지 않는 경우
            throw ProductException.NOT_FOUND.get();
        }

        return ResponseEntity.ok(productService.modify(productDTO));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("productId") Long productId) {
        productService.remove(productId);
        return ResponseEntity.ok(Map.of("message", "success"));
    }

    @GetMapping                         //목록
    public ResponseEntity<Page<ProductDTO>> getList(@Validated PageRequestDTO pageRequestDTO){
        log.info("getList() ----- " + pageRequestDTO);         //로그로 출력
        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }

}
