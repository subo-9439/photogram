package com.example.photogram.repository;

import com.example.photogram.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment,Long> {


    //리턴값으로 받을 수가없음 그러기 떄문에 JpaRepository를 써야함
//    @Modifying
//    @Query(value = "INSERT INTO comment(content, imageId, userId, createDate) VALUES(:content, :imageId, :userId, now())",nativeQuery = true)
//    Comment mSave(String content, Long imageId, Long userId);
}
