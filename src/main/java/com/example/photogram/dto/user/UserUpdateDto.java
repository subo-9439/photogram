package com.example.photogram.dto.user;

import com.example.photogram.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

//뷰에서받은 데이터 -> 업데이트
@Data
public class UserUpdateDto {
    @NotBlank
    private String name;        //필수
    @NotBlank
    private String password;    //필수
    private String webstie;
    private String info;
    private String phone;
    private String gender;



    public User toEntity() {
        return User.builder()
                .name(name)
                .password(password)
                .website(webstie)
                .info(info)
                .phone(phone)
                .gender(gender)
                .build();
    }
}
