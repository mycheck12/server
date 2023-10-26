package com.micheck12.back.chat.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChatListResponseDTO {
  private String code;
  private String msg;
  private List<ChatGetDTO> data;
}
