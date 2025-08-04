package com.ninos.product.service;

import com.ninos.product.dtos.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProduct(ProductDTO product, Long productId);
    ProductDTO getProductById(Long productId);
    void deleteProductById(Long productId);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getProductsByBrandAndName(String brand, String name);
    List<ProductDTO> getProductsByBrand(String brand);
    List<ProductDTO> getProductsByName(String name);
//    List<ProductDTO> getConvertedProducts(List<Product> products);
//    ProductDTO convertToProductDTO(Product product);

}
