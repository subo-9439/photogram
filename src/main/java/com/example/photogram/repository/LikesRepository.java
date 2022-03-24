package com.example.photogram.repository;

import com.example.photogram.domain.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    @Modifying
    @Query(value="Insert INTO likes(image_id, user_id, create_date) VALUES(:imageId, :principalId, now())", nativeQuery = true)
    int mLikes(Long imageId,Long principalId);

    @Modifying
    @Query(value="DLELTE FROM likes WHERE image_id = :imageId AND user_id = :principalId", nativeQuery = true)
    int mUnLikes(Long imageId,Long principalId);

}
