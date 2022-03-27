package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.dto.user.UserProfileDto;
import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.handler.ex.CustomException;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.repository.ImageRepository;
import com.example.photogram.repository.SubscribeRepository;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //1.select를 하더라도 변경된내용이 있으면 flush가 일어난다.(더티체킹)
    //그러나 readOnly = true를 해준다면 변경감지를 하지않고 실행하게해준다
    //2.고립성
    @Transactional(readOnly = true)
    public UserProfileDto userProfile(Long pageUserId,Long principalId){
        UserProfileDto dto = new UserProfileDto();
        User userEntity = userRepository.findById(pageUserId).orElseThrow( ()-> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        dto.setPageOwnerState(pageUserId==principalId);
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId,pageUserId); //1, 0
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState==1);
        dto.setSubscribeCount(subscribeCount);

        //사진 좋아요 개수 추가
        userEntity.getImages().forEach((image)->{
            image.setLikeCount(image.getLikes().size());
        });
        dto.setUser(userEntity);


        return dto;
    }
    @Transactional
    public User userUpdate(Long id, User user){
        //1.영속화
        User userEntity = userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationApiException("찾을수 없는아이디입니다.");
        });


        //2.영속화된 오브젝트를 수정 - 더티체킹 (업데이트)
        //패스워드는 인코딩해서 db에 넣어줘야한다.
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());        userEntity.setPassword(user.getPassword());

        userEntity.setName(user.getName());
        userEntity.setPassword(encPassword);
        userEntity.setInfo(user.getInfo());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    }
    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User userProfileImageChange(Long principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        //통신 예외처리
        try{
            Files.write(imageFilePath,profileImageFile.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);
        return userEntity;
    }
}
