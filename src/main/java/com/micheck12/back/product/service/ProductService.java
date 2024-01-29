package com.micheck12.back.product.service;

import com.micheck12.back.member.entity.Member;
import com.micheck12.back.product.dto.ProductDto;
import com.micheck12.back.product.dto.ProductResponseDto;

public interface ProductService {

    ProductResponseDto getProduct(Long id);
    ProductResponseDto saveProduct(ProductDto productDto);
    ProductResponseDto updateProduct(Long id, ProductDto productDto);
    String likeProduct(Long id, Member member);

}
