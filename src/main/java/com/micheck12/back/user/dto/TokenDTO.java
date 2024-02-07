package com.micheck12.back.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class TokenDTO {

  private String accessToken;
  private String refreshToken;
}
