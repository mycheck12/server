package com.micheck12.back.user.controller;

import com.micheck12.back.user.dto.LoginRequestDTO;
import com.micheck12.back.common.dto.ResponseDTO;
import com.micheck12.back.user.dto.TokenDTO;
import com.micheck12.back.user.dto.UserRegisterRequestDTO;
import com.micheck12.back.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

  private final AuthService authService;

  @Value("${jwt.AUTHORIZATION_HEADER}")
  private String AUTHORIZATION_HEADER;

  @Value("${jwt.REFRESH_HEADER}")
  private String REFRESH_HEADER;

  @Value("${jwt.PREFIX}")
  private String PREFIX;

  // 첫 로그인 또는 access token, refresh token 모두 유효하지 않을 때
  @PostMapping("/register")
  public ResponseEntity<ResponseDTO<Long>> register(@RequestBody @Valid UserRegisterRequestDTO request) {
    ResponseDTO<Long> response = authService.register(request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<Boolean> login(@RequestBody @Valid LoginRequestDTO request, HttpServletResponse response) {
    TokenDTO tokenDTO = authService.login(request);
    response.setHeader(AUTHORIZATION_HEADER, PREFIX + tokenDTO.getAccessToken());
    response.setHeader(REFRESH_HEADER, PREFIX + tokenDTO.getRefreshToken());
    log.info(String.valueOf(response));

    return ResponseEntity.status(HttpStatus.OK).body(true);
  }

  // refresh token 을 기반으로 access token 재발급
  @PostMapping("/reissue-access-token")
  public ResponseEntity<Boolean> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
    log.info("{controller} refresh: {}, access: {}", request.getHeader(REFRESH_HEADER), request.getHeader(AUTHORIZATION_HEADER));
    String newAccessToken = authService.reissueAccessToken(new TokenDTO(request.getHeader(AUTHORIZATION_HEADER), request.getHeader(REFRESH_HEADER)));
    response.setHeader(AUTHORIZATION_HEADER, PREFIX + newAccessToken);
    return ResponseEntity.status(HttpStatus.OK).body(true);
  }

  /*
  TODO: 아이디 중복 체크
   */
}
