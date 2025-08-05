package com.ninos.image.service;

import com.ninos.image.dtos.ImageDTO;
import com.ninos.image.entity.Image;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ImageService {

    Image getImageById(Long imageId);
    void deleteImageById(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);

}
