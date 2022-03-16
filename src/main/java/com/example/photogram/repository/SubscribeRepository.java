package com.example.photogram.repository;

import com.example.photogram.domain.subscribe.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe,Long> {

    //JPA를 이용해서 save를 하는게 아닌 직접 query를 짜주는거라
    //createDate 칼럽을 직접 넣어줘야함
    @Query(value = "INSERT INTO subscribe(from_user_id, to_user_id, create_date) VALUES(:fromUserId, :toUserId, now())",nativeQuery = true)
    @Modifying
    void mDoSubScribe(Long fromUserId, Long toUserId);
    //int형엔 성공 (변경된 행의 개수), 실패 -1

    @Query(value = "DELETE FROM subscribe WHERE from_user_id=:fromUserId AND to_user_id=:toUserId",nativeQuery = true)
    @Modifying
    void mUndoSubScribe(Long fromUserId, Long toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE from_user_id = :principalId AND to_user_id = :pageUserId",nativeQuery = true)
    int mSubscribeState(Long principalId,Long pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE from_user_id = :pageUserId",nativeQuery = true)
    int mSubscribeCount(Long pageUserId);
}
