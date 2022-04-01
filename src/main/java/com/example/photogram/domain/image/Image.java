package com.example.photogram.domain.image;

import com.example.photogram.domain.User;
import com.example.photogram.domain.comment.Comment;
import com.example.photogram.domain.likes.Likes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;//문구

    private String postImageUrl;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name="user_id")
    @ManyToOne(fetch = FetchType.EAGER) //이미지를 select하면 조인해서 User정보를 같이 들고옴
    private User user;

    //좋아요
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    @Transient//영속성 추가x
    private boolean likeState;

    //댓글 무한참조 방지
    //순서대로
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    @Transient
    private int likeCount;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
