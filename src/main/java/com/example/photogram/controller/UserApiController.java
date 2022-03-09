package com.example.photogram.controller;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.User;
import com.example.photogram.dto.CMRespDto;
import com.example.photogram.dto.user.UserUpdateDto;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserApiController  {

    private final UserService userService;

    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable long id,
            @Valid UserUpdateDto userUpdateDto,
            BindingResult bindingResult,// 꼭 @Valid다음 파라미터에 적어놔야함
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(bindingResult.hasErrors()){
            Map<String,String> errMap = new HashMap<>();

            for(FieldError error : bindingResult.getFieldErrors()){
                errMap.put(error.getField(),error.getDefaultMessage());
            }
            throw new CustomValidationApiException("유효성검사 실패함",errMap);
        }else {
            User userEntity = userService.userUpdate(id,userUpdateDto.toEntity());
            //세션정보 업데이트
            principalDetails.setUser(userEntity);
            return new CMRespDto<User>(1,"회원수정완료", userEntity);
        }
    }
}
