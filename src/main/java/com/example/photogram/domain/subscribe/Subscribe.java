package com.example.photogram.domain.subscribe;

import com.example.photogram.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "subscirbe_uk",
                        columnNames = {"fromUserId","toUserId"}
                )
        }
)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="fromUserId")
    @ManyToOne
    private User formUser;

    @JoinColumn(name="toUserId")
    @ManyToOne
    private User toUser;


    private LocalDateTime createDate;


    @PrePersist // db에 insert되기 직전에 실행
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }
}
