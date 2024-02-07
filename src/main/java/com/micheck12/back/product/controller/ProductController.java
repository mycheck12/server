package com.micheck12.back.product.controller;

import com.micheck12.back.member.entity.Member;
import com.micheck12.back.product.dto.ProductDto;
import com.micheck12.back.product.dto.ProductResponseDto;
import com.micheck12.back.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id,
                                                         HttpServletRequest req,
                                                         HttpServletResponse res) {

        Cookie cookie = hitValidation(id, req);
        ProductResponseDto response = productService.getProduct(id);
        if (cookie != null) res.addCookie(cookie);
        return ResponseEntity.ok(response);
    }

    private Cookie hitValidation(Long id, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String[] productIds = cookie.getValue().split("_");
                if (!Arrays.asList(productIds).contains(String.valueOf(id))) {
                    productService.addHit(id);
                    cookie.setValue(cookie.getValue() + "_" + id);
                    return cookie;
                }
                return null;
            }
        }

        productService.addHit(id);
        Cookie cookie = new Cookie("productIds", String.valueOf(id));
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        return cookie;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> saveProduct(ProductDto productDto) {

        ProductResponseDto response = productService.saveProduct(productDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, ProductDto productDto) {

        ProductResponseDto response = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<String> likeProduct(@PathVariable Long id) {
        // TODO: 로그인 중인 유저 얻어오기
        Member member = new Member(1L);

        String result = productService.likeProduct(id, member);
        return ResponseEntity.ok(result);
    }

}
