package com.ninos.image.service;

import com.ninos.image.dtos.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    ImageDTO getImageById(Long imageId);
    void deleteImageById(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);

}
