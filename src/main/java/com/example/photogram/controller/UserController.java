package com.example.photogram.controller;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.dto.user.UserProfileDto;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable Long pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserProfileDto dto = userService.userProfile(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", dto);
        return "user/profile";
    }

    //Session : SecurityContextHolder -> Context-> Authentication -> principalDetails
    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //1.
//        System.out.println("세션정보: " + principalDetails.getUser());

        //2. 비추천
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //PrincipalDetails principalDetails1 = (PrincipalDetails) auth.getPrincipal();
        //System.out.println("직접 찾은 세션정보" + principalDetails1.getUser());

        //시큐리티 세션을 참조하는 방식 채택
        //model.addAttribute("principal",principalDetails.getUser());

        return "user/update";
    }
}
