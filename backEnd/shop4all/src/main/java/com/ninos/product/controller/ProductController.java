package com.ninos.product.controller;

import com.ninos.product.dtos.ProductDTO;
import com.ninos.product.service.ProductService;
import com.ninos.response.ApiResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("OK", products));
    }


    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("id") Long id){
            ProductDTO productDTO = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("OK", productDTO));
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDTO productDTO) {
            ProductDTO productDTO1 = productService.addProduct(productDTO);
            return ResponseEntity.ok(new ApiResponse("Product added successfully", productDTO1));
    }


    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductDTO productDTO,
                                                     @PathVariable Long productId) {
            ProductDTO productDTO1 = productService.updateProduct(productDTO, productId);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully", productDTO1));
    }


    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully", productId));
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName,
                                                                @RequestParam String productName) {
        List<ProductDTO> productDTOS = productService.getProductsByBrandAndName(brandName, productName);
        return ResponseEntity.ok(new ApiResponse("success", productDTOS));
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,
                                                                    @RequestParam String brand) {
        List<ProductDTO> productDTOS = productService.getProductsByCategoryAndBrand(category, brand);
        return ResponseEntity.ok(new ApiResponse("success", productDTOS));
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        List<ProductDTO> productDTOS = productService.getProductsByName(name);
        return ResponseEntity.ok(new ApiResponse("success", productDTOS));
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        List<ProductDTO> productDTOS = productService.getProductsByBrand(brand);
        return ResponseEntity.ok(new ApiResponse("success", productDTOS));
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductsByCategory(@PathVariable String category) {
        List<ProductDTO> productDTOS = productService.getProductsByCategory(category);
        return ResponseEntity.ok(new ApiResponse("success", productDTOS));
    }


}
