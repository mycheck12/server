package com.micheck12.back.chat.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChatAddResponseDTO {
  private String code;
  private String msg;
  private String data;
}
