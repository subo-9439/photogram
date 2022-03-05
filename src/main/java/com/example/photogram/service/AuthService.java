package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;

    public User signupService(User user){
        User userEntity = userRepository.save(user);//회원가입 진행
        return userEntity;

    }
}
