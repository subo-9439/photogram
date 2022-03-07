package com.example.photogram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//공통 responseDto
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
    private int code;//1(success),-1(fail)
    private String message;
    private T data;
}
