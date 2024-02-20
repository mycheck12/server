package com.micheck12.back.product.service;

import com.micheck12.back.product.dto.ProductDto;
import com.micheck12.back.product.dto.ProductResponseDto;
import com.micheck12.back.user.entity.User;

public interface ProductService {

    ProductResponseDto getProduct(Long id);
    ProductResponseDto saveProduct(ProductDto productDto);
    ProductResponseDto updateProduct(Long id, ProductDto productDto);
    void addHit(Long id);
    String likeProduct(Long id, User user);

}
