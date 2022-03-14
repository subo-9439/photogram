package com.example.photogram.dto.user;


import com.example.photogram.domain.User;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
    private boolean pageOwnerState;
    private int imageCount;
    private boolean subscribeState;
    private int subscribeCount;
    private User user;
}