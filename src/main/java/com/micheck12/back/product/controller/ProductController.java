package com.micheck12.back.product.controller;

import com.micheck12.back.product.dto.ProductDto;
import com.micheck12.back.product.dto.ProductResponseDto;
import com.micheck12.back.product.service.ProductService;
import com.micheck12.back.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
                if (cookie.getName().equals("productIds")) {
                    if (!cookie.getValue().contains("[" + id + "]")) {
                        productService.addHit(id);
                        cookie.setValue(cookie.getValue() + "[" + id + "]");
                        return cookie;
                    }
                    return null;
                }
            }
        }

        productService.addHit(id);
        Cookie cookie = new Cookie("productIds", "[" + id + "]");
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
        User user = null;

        String result = productService.likeProduct(id, user);
        return ResponseEntity.ok(result);
    }

}
