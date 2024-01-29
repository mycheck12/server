package com.micheck12.back.product.repository;

import com.micheck12.back.product.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.product.id = :productId AND l.member.id = :memberId")
    Optional<Like> findByProductAndMember(Long productId, Long memberId);

}
