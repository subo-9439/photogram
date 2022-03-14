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
                        name = "subscribe_uk",
                        columnNames = {"FROM_USER_ID","TO_USER_ID"}
                )
        }
)
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="FROM_USER_ID")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name="TO_USER_ID")
    @ManyToOne
    private User toUser;


    private LocalDateTime createDate;


    @PrePersist // db에 insert되기 직전에 실행
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }
}
