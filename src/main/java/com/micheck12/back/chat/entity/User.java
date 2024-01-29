package com.micheck12.back.chat.entity;

import com.micheck12.back.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "user")
@DynamicUpdate
/* 삭제된 데이터는 가져오지 않음 */
@Where(clause = "deleted_at is NULL")
/* 삭제요청이 들어오면 실제로 데이터를 삭제하지 않고 삭제 시간을 기록한다. */
@SQLDelete(sql = "update user set deleted_at = CURRENT_TIMESTAMP where user_id = ?")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false, unique = true)
  private Long userId;

  @Column(name = "username", nullable = false, unique = false)
  private String username;

  @Column(name = "password", nullable = false, unique = false)
  private String password;

  @Column(name = "name", nullable = false, unique = false)
  private String name;

  @Column(name = "nickname", nullable = false, unique = true)
  private String nickname;

  @Column(name = "email", nullable = true, unique = true)
  private String email;

  /*
  private String address;
   */

  @OneToMany(mappedBy = "user")
  private List<UserChat> chats = new ArrayList<>();

  // 사용자의 권한
  @Column(name = "roles", nullable = false, length = 30)
  private String roles;

  // roles(권한) 데이터가 여러개 있다면 ,로 이어져있어 분리 후 List 구현 객체로 반환
  public List<String> getRoleList() {
    if (this.roles != null && this.roles.length() > 0) {
      return Arrays.asList(this.roles.split(","));
    }
    return new ArrayList<>();
  }

  @Builder
  public User(String username, String password, String name, String nickname, String email, String roles) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.email = email;
    this.roles = roles;
  }
}
