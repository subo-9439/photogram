package com.example.photogram.domain;

import com.example.photogram.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    //1 연관관계에 종속된다. 2 테이블에 컬럼을 만들지않는다.
    //User를 선택할 때 해당 User id로 등록된 image들을 다가져온다.
    //Lazy일떈 User를 선택할 때는 해당 user id로 등록된 image들을 가져오지 않는다.
    // 대신 getImages() 함수가 호출 될 때 가져온다
    //Eager = User를 선택할 때 해당 User id로 등록된 image들을 전부 Join해서 겨자온다.
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"})//Image클래스 내부의 user를 제외하고 parsing을해준다.
    private List<Image> images;

    private LocalDateTime createDate;
    @PrePersist // db에 insert되기 직전에 실행
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }
}
