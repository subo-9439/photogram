package com.example.photogram.service;

import com.example.photogram.domain.likes.Likes;
import com.example.photogram.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public void likes(Long imageId, Long principalId) {
        likesRepository.mLikes(imageId,principalId);
    }

    @Transactional
    public void unLikes(Long imageId, Long principalId) {
        likesRepository.mUnLikes(imageId,principalId);
    }
}
