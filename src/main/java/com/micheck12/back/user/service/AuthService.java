package com.micheck12.back.user.service;

import com.micheck12.back.user.dto.LoginRequestDTO;
import com.micheck12.back.common.dto.ResponseDTO;
import com.micheck12.back.user.dto.TokenDTO;
import com.micheck12.back.user.dto.UserRegisterRequestDTO;

public interface AuthService {

  ResponseDTO<Long> register(UserRegisterRequestDTO request);
  TokenDTO login(LoginRequestDTO request);
  String reissueAccessToken(TokenDTO tokenDTO);
}
