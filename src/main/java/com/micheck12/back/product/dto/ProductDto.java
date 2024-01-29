package com.micheck12.back.product.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ProductDto {

    private String name;
    private int price;
    private String info;
    private List<MultipartFile> images = new ArrayList<>();

}
