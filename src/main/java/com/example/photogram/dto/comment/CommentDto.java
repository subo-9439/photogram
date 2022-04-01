package com.example.photogram.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


// NotNull = Null값 체크
// NotEmpty = 빈값이거나 null 체크
// NotBlank = 빈값이거나 null 체크 그리고 스페이스까지
@Data
public class CommentDto {
    @NotBlank
    private String content;
    @NotNull
    private Long imageId;

}
