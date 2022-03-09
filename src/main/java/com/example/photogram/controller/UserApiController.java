package com.example.photogram.controller;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.User;
import com.example.photogram.dto.CMRespDto;
import com.example.photogram.dto.user.UserUpdateDto;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController  {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable long id, UserUpdateDto userUpdateDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User userEntity = userService.userUpdate(id,userUpdateDto.toEntity());
        //세션정보 업데이트
        principalDetails.setUser(userEntity);
        return new CMRespDto<User>(1,"회원수정완료", userEntity);
    }
}
