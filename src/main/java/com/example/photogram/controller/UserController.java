package com.example.photogram.controller;

import com.example.photogram.config.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/user/{id}")
    public String profile(@PathVariable int id) {
        return "user/profile";
    }

    //Session : SecurityContextHolder -> Context-> Authentication -> principalDetails
    @GetMapping("/user/{id}/update/")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //1.
        System.out.println("세션정보: " + principalDetails.getUser());

        //2.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails1 = (PrincipalDetails) auth.getPrincipal();
        System.out.println("직접 찾은 세션정보" + principalDetails1.getUser());
        return "user/update";
    }
}
