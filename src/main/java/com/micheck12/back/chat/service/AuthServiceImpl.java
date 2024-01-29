package com.micheck12.back.chat.service;

import com.micheck12.back.chat.config.JWT.JwtTokenProvider;
import com.micheck12.back.chat.dto.LoginRequestDTO;
import com.micheck12.back.chat.dto.ResponseDTO;
import com.micheck12.back.chat.dto.TokenDTO;
import com.micheck12.back.chat.dto.UserRegisterRequestDTO;
import com.micheck12.back.chat.entity.User;
import com.micheck12.back.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @Override
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
  public TokenDTO login(LoginRequestDTO request) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    TokenDTO tokenDTO = jwtTokenProvider.generateToken(authentication);

    log.info("token: {}", tokenDTO);

    /*
    TODO: key-value 형식의 DB에 refresh token 저장해두는 로직 필요
     */

    return tokenDTO;
  }
}
