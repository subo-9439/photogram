package com.example.photogram.controller;

import com.example.photogram.domain.User;
import com.example.photogram.dto.auth.SignupDto;
import com.example.photogram.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService; //DI

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }


    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto){ //key=value(x-www-form-urlencoded

        User user = signupDto.toEntity();// DTO -> user
        User userEntity = authService.signupService(user); // db저장

        return "auth/signin";
    }
}
