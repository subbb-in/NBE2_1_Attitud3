package edu.example.dev_coffee2.service;

import edu.example.dev_coffee2.dto.PageRequestDTO;
import edu.example.dev_coffee2.dto.ProductDTO;
import edu.example.dev_coffee2.dto.ProductListDTO;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;
    
    // 등록
    public ProductDTO register(ProductDTO productDTO){ // 외부에서 DTO로 데이터 전송함
        Product product = productDTO.toEntity(); // 전달받은 DTO 데이터를 entity 데이터로 변환
        productRepository.save(product); // 데이터 검증 문제가 없다면 entity 객체를 맵핑된 DB에 저장
        return new ProductDTO(product); // 저장된 entity 객체를 다시 dto로 변환 후 사용자에게 전송
    }
    
    // 조회
    public ProductDTO read(Long id){
        Optional<ProductDTO> productDTO = productRepository.getProductDTO(id);
        return productDTO.orElseThrow(() ->
                new EntityNotFoundException("Product " + id + " NOT FOUND")
        );
    }

    // 삭제
    public void remove(Long id){
        Optional<Product> product = productRepository.findById(id);
        Product removeProduct = product.orElseThrow(() ->
                new EntityNotFoundException("Product " + id +  " NOT FOUND"));
        productRepository.delete(removeProduct);
    }

    // 수정
    public ProductDTO modfiy(ProductDTO productDTO){
        Optional<Product> product = productRepository.findById(productDTO.getProductId());
        Product modifyProduct = product.orElseThrow(() ->
                new EntityNotFoundException("Product " + productDTO.getProductId() +  " NOT FOUND"));

        modifyProduct.changeCategory(productDTO.getCategory());
        modifyProduct.changeProductName(productDTO.getProductName());
        modifyProduct.changePrice(productDTO.getPrice());
        modifyProduct.changeDescription(productDTO.getDescription());

        return new ProductDTO(modifyProduct);
    }

    //상품 목록
    public Page<ProductDTO> getList(PageRequestDTO pageRequestDTO) { //목록

            Sort sort = Sort.by("id").descending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.listAll(pageable );

    }

}
