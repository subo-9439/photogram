package com.example.photogram.controller;

import com.example.photogram.domain.User;
import com.example.photogram.dto.auth.SignupDto;
import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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


    //validation @Valid, BindingResult
    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult){ //key=value(x-www-form-urlencoded
        //Aop처리
        User user = signupDto.toEntity();// DTO -> user
        User userEntity = authService.signupService(user); // db저장
        return "auth/signin";
    }
}
