package com.micheck12.back.user.config.JWT;

import com.micheck12.back.user.auth.PrincipalDetails;
import com.micheck12.back.user.dto.TokenDTO;
import com.micheck12.back.user.entity.User;
import com.micheck12.back.common.exception.CustomException;
import com.micheck12.back.common.util.error.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider implements InitializingBean {

  @Value("${jwt.secret}")
  private String secret;

  private static final String AUTHORITIES_KEY = "auth";
  private static final String PREFIX = "Bearer";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;
  private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 3;

  private Key key;

  @Override
  public void afterPropertiesSet() throws Exception {
    byte[] encodedKey = Base64.getEncoder().encode(secret.getBytes());
    this.key = Keys.hmacShaKeyFor(encodedKey);
  }

  public TokenDTO generateToken(Authentication authentication) {
    String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    long now = new Date().getTime();

    String accessToken = Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

    String refreshToken = Jwts.builder()
            .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

    log.info("accessToken : {}", accessToken);
    log.info("refreshToken: {}", refreshToken);

    return TokenDTO.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);

    if (claims.get(AUTHORITIES_KEY) == null) {
      throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
    }

    PrincipalDetails principal = new PrincipalDetails(
            User.builder()
                    .name(claims.getSubject())
                    .roles(claims.get(AUTHORITIES_KEY).toString())
                    .build());

    return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token);
      log.info("유효한 토큰");
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("유효하지 않은 토큰");
    } catch (ExpiredJwtException e) {
      log.info("만료된 토큰");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 토큰");
    } catch (IllegalArgumentException e) {
      log.info("잘못된 토큰");
    }

    return false;
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(accessToken)
              .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
