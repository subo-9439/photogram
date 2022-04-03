package com.example.photogram.handler.aop;

import com.example.photogram.handler.ex.CustomValidationApiException;
import com.example.photogram.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

//Aop
@Component
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.example.photogram.controller.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //매개변수를 찾아줌
        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof BindingResult){
                System.out.println("유효성검사를 하는 함수입니다.");
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()){
                    Map<String,String> errorMap = new HashMap<>();

                    for(FieldError error: bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(),error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사 실패함",errorMap);
                }
            }
            System.out.println(arg);
        }
        //proceedingJoinPoint => profile 함수의 모든 곳에 접근 할 수 있는 변수
        // 실행할 함수보다 먼저저실행
       return proceedingJoinPoint.proceed(); // 해당 함수가 실행됨
    }

    @Around("execution(* com.example.photogram.controller.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();

        for(Object arg : args){
            if(arg instanceof  BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasErrors()) {
                    Map<String, String> errMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성검사 실패함", errMap);
                }
            }
            System.out.println(arg);
        }
        return proceedingJoinPoint.proceed();
    }
}
