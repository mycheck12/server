package com.micheck12.back.chat.controller;

import com.micheck12.back.chat.dto.ChatAddRequestDTO;
import com.micheck12.back.chat.dto.ChatAddResponseDTO;
import com.micheck12.back.chat.dto.ChatListResponseDTO;
import com.micheck12.back.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/chat")
@RestController
public class ChatController {

  private final ChatService chatService;

  // 생성된 전체 채팅방 목록 조회
  @GetMapping
  public ResponseEntity<ChatListResponseDTO> getList() {
    ChatListResponseDTO response = chatService.getList();
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  // 채팅방 생성
  @PostMapping
  public ResponseEntity<ChatAddResponseDTO> createChat(@RequestBody @Valid ChatAddRequestDTO request) {
    ChatAddResponseDTO response = chatService.createChat(request);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
