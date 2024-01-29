package com.micheck12.back.product.entity;

import com.micheck12.back.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String info;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
    }

    public void update(String name, int price, String info) {
        this.name = name;
        this.price = price;
        this.info = info;
    }

    @Builder
    public Product(String name, int price, String info) {
        this.name = name;
        this.price = price;
        this.info = info;
    }

}
