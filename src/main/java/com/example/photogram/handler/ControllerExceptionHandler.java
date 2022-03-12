package com.example.photogram.handler;

import com.example.photogram.dto.CMRespDto;
import com.example.photogram.handler.ex.CustomApiException;
import com.example.photogram.handler.ex.CustomException;
import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.handler.ex.CustomValidationException;
import com.example.photogram.util.Script;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //jpa 실패할때
    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e){
        return Script.back(e.getMessage());
    }

    //클라이언트-> script반환
    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){
        if(e.getErrorMap() == null) return e.getMessage();
        else return Script.back(e.getErrorMap().toString());

    }
    //ajax통신 validation
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
    }
    //구독
    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
    }
}
