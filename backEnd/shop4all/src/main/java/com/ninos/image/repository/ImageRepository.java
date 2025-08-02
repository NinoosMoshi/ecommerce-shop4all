package com.ninos.image.repository;

import com.ninos.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findImagesByProductId(Long productId);

}