package com.example.photogram.handler.ex;
import java.util.Map;


//APi호출시 message만 예외처리
public class CustomApiException extends RuntimeException{

    //객체를 구분할 때
    private static final long serialVersionUID = 1L;

    public CustomApiException(String message){
        super(message);
    }


}
