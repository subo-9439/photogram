package com.example.photogram.handler;

import com.example.photogram.dto.CMRespDto;
import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.util.Script;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


@RestControllerAdvice
public class ControllerExceptionHandler {

    //비동기 통신을 할때
//    @ExceptionHandler(CustomValidationException.class)
//    public CMRespDto<?> validationException(CustomValidationException e){
//        return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());
//    }

    //template
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){
        return Script.back(e.getErrorMap().toString());
    }
}
