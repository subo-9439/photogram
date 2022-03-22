package com.example.photogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {//web설정

    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        //file:///D:/workspace/~
        registry.addResourceHandler("/**")
                        .addResourceLocations("classpath:/templates/","classpath:/static/");
        registry
                .addResourceHandler("/upload/**")//view에서 /upload/** 로들어오면
                .addResourceLocations("file:///"+uploadFolder)
                .setCachePeriod(60*10*6)//1시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
