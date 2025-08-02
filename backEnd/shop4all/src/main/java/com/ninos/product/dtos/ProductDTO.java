package com.ninos.product.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ninos.category.entity.Category;
import com.ninos.image.dtos.ImageDTO;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // If a field is null, it will not appear in the resulting output JSON.
@JsonIgnoreProperties(ignoreUnknown = true) // If the JSON contains fields not present in the Java class, they are ignored
public class ProductDTO {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDTO> images;
}

