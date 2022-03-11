package com.example.photogram.service;

import com.example.photogram.domain.subscribe.Subscribe;
import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;

    @Transactional
    public void doSubScribe(Long fromUserId, Long toUserId){
        try{
            subscribeRepository.doSubScribe(fromUserId,toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void undoSubScribe(Long fromUserId, Long toUserId) {
        subscribeRepository.undoSubScribe(fromUserId,toUserId);
    }
}
