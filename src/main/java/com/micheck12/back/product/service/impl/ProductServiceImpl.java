package com.micheck12.back.product.service.impl;

import com.micheck12.back.product.dto.ProductDto;
import com.micheck12.back.product.dto.ProductResponseDto;
import com.micheck12.back.product.entity.Image;
import com.micheck12.back.product.entity.Product;
import com.micheck12.back.product.mapper.ProductMapper;
import com.micheck12.back.product.repository.ProductRepository;
import com.micheck12.back.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final S3UploadService s3UploadService;
    private final ProductMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return mapper.productToProductResponseDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDto saveProduct(ProductDto productDto) {

        Product product = mapper.productDtoToProduct(productDto);
        uploadImages(product, productDto.getImages());

        Product savedProduct = productRepository.save(product);

        return mapper.productToProductResponseDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductDto productDto) {

        Product product = productRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        deleteImages(product);

        product.update(productDto.getName(), productDto.getPrice(), productDto.getInfo());
        uploadImages(product, productDto.getImages());

        Product savedProduct = productRepository.save(product);
        return mapper.productToProductResponseDto(savedProduct);
    }

    private void uploadImages(Product product, List<MultipartFile> multipartFiles) {
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                String imageUrl = s3UploadService.saveFile(multipartFile);
                product.addImage(new Image(multipartFile.getOriginalFilename(), imageUrl));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void deleteImages(Product product) {
        List<Image> imagesToDelete = new ArrayList<>(product.getImages());
        for (Image image : imagesToDelete) {
            s3UploadService.deleteImages(image.getOriginName());
            product.removeImage(image);
        }
    }

}
