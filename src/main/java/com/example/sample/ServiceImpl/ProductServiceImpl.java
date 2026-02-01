package com.example.sample.ServiceImpl;

import com.example.sample.DTO.ProductRequestDTO;
import com.example.sample.DTO.ProductResponseDTO;
import com.example.sample.Entity.Product;
import com.example.sample.Repo.ProductRepo;
import com.example.sample.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setProductName(requestDTO.getProductName());
        product.setProductType(requestDTO.getProductType());
        product.setAmount(requestDTO.getAmount());
        productRepo.save(product);

        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getProductType(),
                product.getAmount()
        );
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepo.findById(id).
                orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getProductType(),
                product.getAmount()
        );
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(p -> new ProductResponseDTO(
                        p.getId(),
                        p.getProductName(),
                        p.getProductType(),
                        p.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long id , ProductRequestDTO requestDTO) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
        product.setProductName(requestDTO.getProductName());
        product.setProductType(requestDTO.getProductType());
        product.setAmount(requestDTO.getAmount());

        productRepo.save(product);

        return new ProductResponseDTO(
                product.getId(),
                product.getProductName(),
                product.getProductType(),
                product.getAmount()
        );
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)){
            throw new RuntimeException("Product not found with id " + id);
        }
        productRepo.deleteById(id);
    }

}
