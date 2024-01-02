package com.micheck12.back.product.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "image")
@EqualsAndHashCode(of = { "imageUrl" } )
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    @Builder
    public Image(String originName, String imageUrl) {
        this.originName = originName;
        this.imageUrl = imageUrl;
    }

}
