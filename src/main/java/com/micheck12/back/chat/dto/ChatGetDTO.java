package com.micheck12.back.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ChatGetDTO {
  private Long chatId;
  private LocalDateTime createdAt;
}
