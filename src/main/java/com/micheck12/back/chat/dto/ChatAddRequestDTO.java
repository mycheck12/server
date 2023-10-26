package com.micheck12.back.chat.dto;

import com.micheck12.back.chat.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ChatAddRequestDTO {

  public Chat toEntity() {
    return Chat.builder()
            .build();
  }
}
