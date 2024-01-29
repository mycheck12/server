package com.micheck12.back.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
TODO: 검증 단계 필요
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LoginRequestDTO {

  private String username;
  private String password;
}
