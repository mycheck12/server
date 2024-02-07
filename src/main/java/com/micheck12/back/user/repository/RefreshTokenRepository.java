package com.micheck12.back.user.repository;

import com.micheck12.back.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findByUsername(String username);
  Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
