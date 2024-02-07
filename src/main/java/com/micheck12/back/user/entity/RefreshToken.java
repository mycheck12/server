package com.micheck12.back.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "refresh_token")
@SQLDelete(sql = "update refresh_token set deleted_at = CURRENT_TIMESTAMP where user_token_id = ?")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_token_id", nullable = false, unique = true)
  private Long userTokenId;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "refresh_token", nullable = false, unique = true)
  private String refreshToken;

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @Builder
  public RefreshToken(String username, String refreshToken) {
    this.username = username;
    this.refreshToken = refreshToken;
  }
}
