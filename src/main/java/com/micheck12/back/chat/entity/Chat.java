package com.micheck12.back.chat.entity;

import com.micheck12.back.common.entity.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@ToString
@Getter
@Where(clause = "deleted_at is NULL")
@SQLDelete(sql = "update chat set deleted_at = CURRENT_TIMESTAMP where chat_id = ?")
@Table(name = "chat")
@Entity
@DynamicUpdate
public class Chat extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_id", nullable = false, unique = true)
  private Long chatId;

  @Builder
  public Chat() {}
}
