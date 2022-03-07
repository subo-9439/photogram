package com.example.photogram.handler.ex;
import java.util.Map;


//RuntimeException은 String으로만 메세지를 전달하므로 error데이터를 출력할 수 있도록 만들어준다.
public class CustomValidationException extends RuntimeException{

    //객체를 구분할 때
    private static final long serialVersionUID = 1L;
    private Map<String,String> errorMap;

    public CustomValidationException(String message, Map<String,String> errorMap) {
        super(message);//Exception이 상속하고 있는 Throwable getMessage
        this.errorMap = errorMap;
    }

    public Map<String,String> getErrorMap(){
        return errorMap;
    }
}
