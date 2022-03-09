package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional
    public User userUpdate(long id, User user){
        //1.영속화
        User userEntity = userRepository.findById(id).get();

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
