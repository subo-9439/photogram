package com.example.photogram.service;

import com.example.photogram.config.auth.PrincipalDetails;
import com.example.photogram.domain.image.Image;
import com.example.photogram.dto.image.ImageUploadDto;
import com.example.photogram.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    @Value("${file.path}")//springFramework application.yaml적힌 file.path경로
    private String uploadFolder;

    @Transactional
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

    @Transactional(readOnly = true)//영속성 컨텍스트 변경 감지를 해서 , 더티 체킹을 함 flush(반영), readOnly = true를 한다면 flush X
    public List<Image> imageStory(Long principalId) {
        List<Image> images = imageRepository.mStory(principalId);
        return images;
    }
}
