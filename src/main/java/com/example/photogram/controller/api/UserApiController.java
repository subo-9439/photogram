package com.example.photogram.controller.api;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.User;
import com.example.photogram.domain.subscribe.Subscribe;
import com.example.photogram.dto.CMRespDto;
import com.example.photogram.dto.subscribe.SubscribeDto;
import com.example.photogram.dto.user.UserUpdateDto;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.service.SubscribeService;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//구독하기 , 유저정보 업데이트(put방식이기떄문에 비동기로 처리했다.)
@RequiredArgsConstructor
@RestController
public class UserApiController  {

    private final UserService userService;
    private final SubscribeService subscribeService;

    @PutMapping("/api/user/{pirincipalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable Long principalId, MultipartFile profileImageFile,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails){
        User userEntity = userService.userProfileImageChange(principalId,profileImageFile);
        principalDetails.setUser(userEntity);//세션변경해줘야함
        return new ResponseEntity<>(new CMRespDto<>(1,"프로필사진변경 성공",null),HttpStatus.OK);
    }

    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable Long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.subScribeList(principalDetails.getUser().getId(),pageUserId);
        return new ResponseEntity<>(new CMRespDto<>(1,"구독자 정보리스트 가져오기 성공",subscribeDto),HttpStatus.OK);
    }
    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(
            @PathVariable Long id,
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
