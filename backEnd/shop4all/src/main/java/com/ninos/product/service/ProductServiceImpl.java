package com.ninos.product.service;

import com.ninos.cart.entity.Cart;
import com.ninos.cart_item.entity.CartItem;
import com.ninos.cart_item.repository.CartItemRepository;
import com.ninos.category.entity.Category;
import com.ninos.category.repository.CategoryRepository;
import com.ninos.image.dtos.ImageDTO;
import com.ninos.image.entity.Image;
import com.ninos.image.repository.ImageRepository;
import com.ninos.order_item.entity.OrderItem;
import com.ninos.order_item.repository.OrderItemRepository;
import com.ninos.product.dtos.ProductDTO;
import com.ninos.product.entity.Product;
import com.ninos.product.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;


    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        // Check if product with the same name and brand already exists
        boolean productExists = productRepository.existsByNameAndBrand(productDTO.getName(), productDTO.getBrand());
        if (productExists) {
            throw new EntityExistsException(productDTO.getName() + " already exists!");
        }

        // Ensure the category exists or create a new one
        Category category = categoryRepository.findByName(productDTO.getCategory().getName());
        if (category == null) {
            category = new Category(productDTO.getCategory().getName());
            category = categoryRepository.save(category);
        }

        // Manually construct the Product entity from ProductDTO
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setInventory(productDTO.getInventory());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);

        // Persist the product
        Product savedProduct = productRepository.save(product);

        // Map the saved product back to ProductDTO
        return convertSingleProductToDto(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId) {
        // Fetch the existing product or throw if not found
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));

        // Update basic fields
        existingProduct.setName(productDTO.getName());
        existingProduct.setBrand(productDTO.getBrand());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setInventory(productDTO.getInventory());
        existingProduct.setDescription(productDTO.getDescription());

        // Handle category (create if not exists)
        Category category = categoryRepository.findByName(productDTO.getCategory().getName());
        if (category == null) {
            category = categoryRepository.save(new Category(productDTO.getCategory().getName()));
        }
        existingProduct.setCategory(category);

        // Save the updated product
        Product savedProduct = productRepository.save(existingProduct);

        // Convert back to DTO and return
        return convertSingleProductToDto(savedProduct);
    }

    @Override
    public ProductDTO getProductById(Long productId) {

        Product savedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return convertSingleProductToDto(savedProduct);
    }



    @Override
    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found!"));

        // Remove product from cart items
        List<CartItem> cartItems = cartItemRepository.findByProductId(productId);
        for (CartItem cartItem : cartItems) {
            Cart cart = cartItem.getCart();
            cart.removeItem(cartItem);
            cartItemRepository.delete(cartItem);
        }

        // Remove product reference from order items
        List<OrderItem> orderItems = orderItemRepository.findByProductId(productId);
        for (OrderItem orderItem : orderItems) {
            orderItem.setProduct(null);
            orderItemRepository.save(orderItem);
        }

        // Remove product from its category
        Category category = product.getCategory();
        if (category != null) {
            category.getProducts().remove(product);
            product.setCategory(null);
        }

        // Delete product
        productRepository.deleteById(product.getId());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return convertProductToDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findProductsByCategoryNameAndBrand(category, brand);
        return convertProductToDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepository.findProductsByCategoryName(category);
        return convertProductToDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findProductsByBrandAndName(brand,name);
        return convertProductToDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findProductsByBrand(brand);
        return convertProductToDto(products);
    }

    @Override
    public List<ProductDTO> getProductsByName(String name) {
        List<Product> products = productRepository.findProductsByName(name);
        return convertProductToDto(products);
    }


    private ProductDTO convertSingleProductToDto(Product savedProduct) {
        ProductDTO productDTO = modelMapper.map(savedProduct, ProductDTO.class);
        List<Image> images = imageRepository.findImagesByProductId(savedProduct.getId());
        List<ImageDTO> imageDTOS = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();

        productDTO.setImages(imageDTOS);
        return productDTO;
    }


    private List<ProductDTO> convertProductToDto(List<Product> products) {
        // return modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
        return products.stream().map(product -> modelMapper.map(product, ProductDTO.class))
                .toList(); // this way is better

    }





}
