package com.micheck12.back.chat.service;

import com.micheck12.back.chat.dto.LoginRequestDTO;
import com.micheck12.back.chat.dto.ResponseDTO;
import com.micheck12.back.chat.dto.TokenDTO;
import com.micheck12.back.chat.dto.UserRegisterRequestDTO;

public interface AuthService {

  ResponseDTO<Long> register(UserRegisterRequestDTO request);
  TokenDTO login(LoginRequestDTO request);
}
