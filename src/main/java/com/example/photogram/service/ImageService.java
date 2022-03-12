package com.example.photogram.service;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.image.Image;
import com.example.photogram.dto.image.ImageUploadDto;
import com.example.photogram.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    @Value("${file.path}")//springFramework application.yaml적힌 file.path경로
    private String uploadFolder;

    public void imageUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid +"_"+ imageUploadDto.getFile().getOriginalFilename();

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);//nio

        //이미지 파일생성
        try {
            Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }

        // image테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        Image imageEntity = imageRepository.save(image);

    }
}
