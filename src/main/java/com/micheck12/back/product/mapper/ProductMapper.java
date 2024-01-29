package com.micheck12.back.product.mapper;

import com.micheck12.back.product.dto.ProductDto;
import com.micheck12.back.product.dto.ProductResponseDto;
import com.micheck12.back.product.entity.Image;
import com.micheck12.back.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDto productToProductResponseDto(Product product);

    Product productDtoToProduct(ProductDto productDto);

    default List<String> mapImages(List<Image> images) {
        if (images == null) {
            return Collections.emptyList();
        }
        return images.stream()
                .map(Image::getImageUrl)
                .toList();
    }

}
