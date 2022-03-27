package com.example.photogram.service;

import com.example.photogram.domain.comment.Comment;
import com.example.photogram.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment commentWrite(String content, Long imageId, Long userId){
        return null;
    }

    @Transactional
    public void commentRemove(){

    }
}
