package com.micheck12.back.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ProductResponseDto {

    private Long id;

    private String name;

    private int price;

    @JsonProperty(value = "image_url")
    private List<String> images;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    private String info;

    private int hit;

}
