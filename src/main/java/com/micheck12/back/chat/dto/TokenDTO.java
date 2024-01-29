package com.micheck12.back.chat.dto;

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
