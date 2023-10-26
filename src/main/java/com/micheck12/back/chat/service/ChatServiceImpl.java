package com.micheck12.back.chat.service;

import com.micheck12.back.chat.dto.ChatAddRequestDTO;
import com.micheck12.back.chat.dto.ChatAddResponseDTO;
import com.micheck12.back.chat.dto.ChatListResponseDTO;
import com.micheck12.back.chat.entity.Chat;
import com.micheck12.back.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

  private final ChatRepository chatRepository;

  @Override
  public ChatAddResponseDTO createChat(ChatAddRequestDTO dto) {
    Chat chat = chatRepository.save(
            Chat.builder()
                    .build()
    );
    return ChatAddResponseDTO.builder()
            .code(HttpStatus.OK.toString())
            .msg("방 생성 성공")
            .data(chat.getChatId() + "")
            .build();
  }

  @Override
  public ChatListResponseDTO getList() {
    return ChatListResponseDTO.builder()
            .code(HttpStatus.OK.toString())
            .msg("방 목록 조회")
            .data(chatRepository.findAll().stream().map(chat ->  entityToDto(chat)).toList())
            .build();
  }
}
