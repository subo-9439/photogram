package com.example.photogram.service;

import com.example.photogram.dto.subscribe.SubscribeDto;
import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.LongType;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;



@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; //Repository EntityManager를 구현해서 만들어져 있는 구현체


    //구독리스트 뽑기
    @Transactional(readOnly = true)
    public  List<SubscribeDto> subScribeList(Long principalId, Long pageUserId) {

        //native query
        System.out.println("로그인아이디"+principalId+" 페이지아이디" + pageUserId);
        //u 는 페이지유저
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profile_image_url, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE from_user_id = ? AND to_user_id = u.id), 1, 0) subscribe_state, ");
        sb.append("if ((?=u.id), 1, 0) equal_user_state ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.to_user_id ");
        sb.append("WHERE s.from_user_id = ? "); // 세미콜론 첨부하면 안됨

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);
        //1.물음표 principalId
        //2.물음표 principalId
        //마지막 물음표 pageUserId
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDto = result.list(query,SubscribeDto.class);

        return subscribeDto;
    }

    @Transactional
    public void doSubScribe(Long fromUserId, Long toUserId){
        try{
            subscribeRepository.mDoSubScribe(fromUserId,toUserId);
        } catch (Exception e){
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void undoSubScribe(Long fromUserId, Long toUserId) {
        subscribeRepository.mUndoSubScribe(fromUserId,toUserId);
    }


}
