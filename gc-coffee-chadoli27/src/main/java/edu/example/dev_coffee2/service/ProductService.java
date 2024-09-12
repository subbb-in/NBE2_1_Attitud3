package edu.example.dev_coffee2.service;

import edu.example.dev_coffee2.dto.PageRequestDTO;
import edu.example.dev_coffee2.dto.ProductDTO;
import edu.example.dev_coffee2.entity.Product;
import edu.example.dev_coffee2.exception.ProductException;
import edu.example.dev_coffee2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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

    public ProductDTO register(ProductDTO productDTO){   //등록
        try {
            Product product = productDTO.toEntity();
            productRepository.save(product);
            return new ProductDTO(product);
        } catch(Exception e) {                  //상품 등록 시 예외가 발생한 경우
            log.error("--- " + e.getMessage()); //에러 로그로 발생 예외의 메시지를 기록하고
            throw ProductException.NOT_REGISTERED.get();  //예외 메시지를 Product NOT Registered로 지정하여 ProductTaskException 발생시키기
        }
    }

    public ProductDTO read(Long productId){  // 조회
        Optional<ProductDTO> productDTO = productRepository.getProductDTO(productId);
        return productDTO.orElseThrow(ProductException.NOT_FOUND::get);
    }

    public ProductDTO modify(ProductDTO productDTO){
        Optional<Product> foundProduct = productRepository.findById(productDTO.getProductId());
        Product product = foundProduct.orElseThrow(ProductException.NOT_FOUND::get);

        try {
            product.changeProductName(productDTO.getProductName());
            product.changePrice(productDTO.getPrice());
            product.changeDescription(productDTO.getDescription());
            product.changeCategory(productDTO.getCategory());

            return new ProductDTO(product);

        }catch (Exception e){
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_MODIFIED.get();
        }
    }

    public void remove(Long productId){
        Optional<Product> foundProduct = productRepository.findById(productId);
        Product product = foundProduct.orElseThrow(ProductException.NOT_FOUND::get);

        try{
            productRepository.delete(product);
        }catch (Exception e){
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_REMOVED.get();
        }
    }

    public Page<ProductDTO> getList(PageRequestDTO pageRequestDTO){
        try {
            Sort sort = Sort.by("productId");
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.listAll(pageable);
        }catch (Exception e){
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_FETCHED.get();
        }
    }



}
