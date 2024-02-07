package com.micheck12.back.user.entity;

import com.micheck12.back.chat.entity.Chat;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "user_chat")
public class UserChat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_chat_id", nullable = false, unique = true)
  private Long userChatId;

  @ManyToOne
  @JoinColumn(name = "chat_id")
  private Chat chat;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
