package com.ninos.image.controller;

import com.ninos.image.dtos.ImageDTO;
import com.ninos.image.entity.Image;
import com.ninos.image.repository.ImageRepository;
import com.ninos.image.service.ImageService;
import com.ninos.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam("file") List<MultipartFile> file,
                                                   @RequestParam("productId") Long productId) {
            List<ImageDTO> imageDTOS = imageService.saveImages(productId, file);
            return ResponseEntity.ok(new ApiResponse("Images uploaded successfully", imageDTOS));
    }

    // "/api/v1/images/image/download/"
    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int)image.getImage().length()));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"").body(resource);
    }


    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam MultipartFile file,
                                                   @PathVariable Long imageId){

            imageService.updateImage(file, imageId);
            return ResponseEntity.ok(new ApiResponse("Image updated successfully", null));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
            imageService.deleteImageById(imageId);
            return ResponseEntity.ok(new ApiResponse("Image deleted successfully", null));
    }


}
