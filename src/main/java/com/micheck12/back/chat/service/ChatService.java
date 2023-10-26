package com.micheck12.back.chat.service;

import com.micheck12.back.chat.dto.ChatAddRequestDTO;
import com.micheck12.back.chat.dto.ChatAddResponseDTO;
import com.micheck12.back.chat.dto.ChatGetDTO;
import com.micheck12.back.chat.dto.ChatListResponseDTO;
import com.micheck12.back.chat.entity.Chat;

public interface ChatService {

  ChatAddResponseDTO createChat(ChatAddRequestDTO dto);
  ChatListResponseDTO getList();

  default ChatGetDTO entityToDto(Chat chat) {
    return ChatGetDTO.builder()
            .chatId(chat.getChatId())
            .createdAt(chat.getCreatedAt())
            .build();
  }
}
