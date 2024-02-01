package com.micheck12.back.user.dto;

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
public class UserRegisterRequestDTO {

  private String username;
  private String password;
  private String name;
  private String nickname;
  private String email;
}
