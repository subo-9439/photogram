package com.example.photogram.domain.likes;


import com.example.photogram.domain.User;
import com.example.photogram.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"image_id","user_id"}
                )
        }
)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JoinColumn(name = "image_id")
    @ManyToOne
    private Image image;


    //무한참조
    //Likes -> User -> Image
    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    private LocalDateTime createDate;

    @PrePersist
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }
}
