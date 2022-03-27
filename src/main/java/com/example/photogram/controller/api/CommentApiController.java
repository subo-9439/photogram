package com.example.photogram.controller.api;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.dto.comment.CommentDto;
import com.example.photogram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@RequestBody CommentDto commentDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(commentDto);
        commentService.commentWrite(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId());//content,imageid,userid
        return null;
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable int id){

        return null;
    }
}
