package com.micheck12.back.user.service;

import com.micheck12.back.common.dto.ResponseDTO;
import com.micheck12.back.common.exception.CustomException;
import com.micheck12.back.user.config.JWT.JwtTokenProvider;
import com.micheck12.back.user.dto.LoginRequestDTO;
import com.micheck12.back.user.dto.TokenDTO;
import com.micheck12.back.user.dto.UserRegisterRequestDTO;
import com.micheck12.back.user.entity.RefreshToken;
import com.micheck12.back.user.entity.User;
import com.micheck12.back.user.repository.RefreshTokenRepository;
import com.micheck12.back.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.micheck12.back.common.util.error.ErrorCode.INVALID_REFRESH_TOKEN;
import static com.micheck12.back.common.util.error.ErrorCode.REFRESH_TOKEN_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final RefreshTokenRepository refreshTokenRepository;

  @Override
  @Transactional
  public ResponseDTO<Long> register(UserRegisterRequestDTO request) {
    User savedUser = userRepository.save(
            User.builder()
                    .username(request.getUsername())
                    .password(bCryptPasswordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .nickname(request.getNickname())
                    .email(request.getEmail())
                    .roles("ROLE_USER")
                    .build());

    return ResponseDTO.<Long>builder()
            .msg("회원가입 성공")
            .data(savedUser.getUserId())
            .build();
  }

  @Override
  @Transactional
  public TokenDTO login(LoginRequestDTO request) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    TokenDTO tokenDTO = jwtTokenProvider.generateToken(authentication);

    log.info("token: {}", tokenDTO);

    Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUsername(request.getUsername());

    // 보관된 refresh token 가 없다면
    if (optionalRefreshToken.isEmpty()) {
      // 인스턴스 생성해 저장
      refreshTokenRepository.save(
              new RefreshToken(
                      request.getUsername(),
                      tokenDTO.getRefreshToken()
              )
      );
    }
    else {
      optionalRefreshToken.get().updateRefreshToken(tokenDTO.getRefreshToken());
    }

    return tokenDTO;
  }

  @Override
  @Transactional
  public String reissueAccessToken(TokenDTO tokenDTO) {
    String refreshToken = jwtTokenProvider.resolveToken(tokenDTO.getRefreshToken());
    log.info("refresh token: {}", refreshToken);

    // refresh token 유효 검증
    if (!jwtTokenProvider.validateToken(refreshToken)) throw new CustomException(INVALID_REFRESH_TOKEN);

    /*
    refresh token 으로 사용자 아이디(username)와 함께 보관된 RefreshToken 객체 불러오기
    없다면 NOT_FOUND 예외 던지기
    */
    RefreshToken refreshTokenWithUsername = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(
            () -> new CustomException(REFRESH_TOKEN_NOT_FOUND)
    );

    /*
    이전의 refresh token 과 다르거나
    refresh token 가 만료되었다면
    재로그인이 필요하다.
    */
    if (!refreshTokenWithUsername.getRefreshToken().equals(refreshToken)) throw new CustomException(INVALID_REFRESH_TOKEN);

    // 만료된 access token 으로 authentication 가져오기
    String accessToken = jwtTokenProvider.resolveToken(tokenDTO.getAccessToken());

    // refresh 로 token 재발급 후 반환
    return jwtTokenProvider.generateToken(jwtTokenProvider.getAuthentication(accessToken)).getAccessToken();
  }
}
