package edu.example.dev_coffee2.controller;

import edu.example.dev_coffee2.dto.PageRequestDTO;
import edu.example.dev_coffee2.dto.ProductDTO;
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

    // 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> read(@PathVariable("id") Long id){
        log.info("--- rade()");
        log.info("--- id : "+id);
        return ResponseEntity.ok(productService.read(id));
    }

    // 등록
    @PostMapping
    public ResponseEntity<ProductDTO> register(@Validated @RequestBody ProductDTO productDTO){
        log.info("--- register()");
        log.info("--- productDTO : " + productDTO);

        return ResponseEntity.ok(productService.register(productDTO));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> modify(@Validated @RequestBody ProductDTO productDTO,
                                             @PathVariable("id") Long id){
        log.info("--- modify()");
        log.info("--- productDTO : " + productDTO);

        productDTO.setProductId(id);
        return ResponseEntity.ok(productService.modfiy(productDTO));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> remove(@PathVariable("id") Long id){
        log.info("--- remove()");
        log.info("--- id : " + id);

        productService.remove(id);        
        return ResponseEntity.ok(Map.of("result", "success")); //삭제 처리 후 출력
    }

    // 상품 목록
    @GetMapping                         //목록
    public ResponseEntity<Page<ProductDTO>> getList(@Validated PageRequestDTO pageRequestDTO){
        log.info("getList() ----- " + pageRequestDTO);         //로그로 출력
        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }
}
