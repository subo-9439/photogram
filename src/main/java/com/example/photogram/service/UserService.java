package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.handler.ex.CustomException;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.repository.ImageRepository;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User userProfile(Long userId){
        // SELECT * from image where userId =:userId;
        User userEntity = userRepository.findById(userId).orElseThrow( ()-> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        return userEntity;
    }
    @Transactional
    public User userUpdate(long id, User user){
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
}
