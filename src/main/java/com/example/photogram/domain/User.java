package com.example.photogram.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String website;     //웹 사이트

    private String info;
    @Column(nullable = false)
    private String email;

    private String phone;

    private String gender;

    private String profileImageUrl;     //사진 주소

    private String role;                // 권한

    private LocalDateTime createDate;

    @PrePersist // db에 insert되기 직전에 실행
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }
}
