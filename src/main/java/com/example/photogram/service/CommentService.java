package com.example.photogram.service;

import com.example.photogram.domain.User;
import com.example.photogram.domain.image.Image;
import com.example.photogram.domain.comment.Comment;
import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.repository.CommentRepository;
import com.example.photogram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment commentWrite(String content, Long imageId, Long userId){

        //Tip (객체를 만들 때 id값만 담아서 insert 할 수 있다. db에서 찾아서 넣지 않는 방법
        //대신 아이디만 있는 빈껎데기를 받는다.
        Image image = new Image();
        image.setId(imageId);

        //유저는 유저정보가 필요하므로 위와같은 방법을 쓰면 안된다.
        User userEntity = userRepository.findById(imageId).orElseThrow(()-> {
            throw new CustomApiException("유저 아이디를 찾을 수 없습니다.");
        });
//        User user = new User();
//        user.setId(userId);

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(userEntity);
        comment.setImage(image);
        return commentRepository.save(comment);
    }

    @Transactional
    public void commentRemove(Long id){
        try{
            commentRepository.deleteById(id);
        }catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
    }
}
