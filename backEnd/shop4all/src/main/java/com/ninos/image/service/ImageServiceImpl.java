package com.ninos.image.service;

import com.ninos.image.dtos.ImageDTO;
import com.ninos.image.entity.Image;
import com.ninos.image.repository.ImageRepository;
import com.ninos.product.dtos.ProductDTO;
import com.ninos.product.entity.Product;
import com.ninos.product.repository.ProductRepository;
import com.ninos.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    @Override
    public ImageDTO getImageById(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image with id " + imageId + " not found!"));
        return modelMapper.map(image, ImageDTO.class);
    }

    @Override
    public void deleteImageById(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image with id " + imageId + " not found!"));
        imageRepository.delete(image);
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image with id " + imageId + " not found!"));

        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ImageDTO> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found!"));

        List<ImageDTO> savedImages = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(savedImage.getId());
                imageDTO.setFileName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
                savedImages.add(imageDTO);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImages;
    }
}
