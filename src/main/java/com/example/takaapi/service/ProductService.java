package com.example.takaapi.service;



import com.example.takaapi.dto.ProductDto;
import com.example.takaapi.exception.ProductNotExistsException;
import com.example.takaapi.model.Category;
import com.example.takaapi.model.Product;
import com.example.takaapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    private Path staticPath = Paths.get("static");
    Path imagePath = Paths.get("images");


    public void createProduct(ProductDto productDto, Category category) throws IOException {
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(productDto.getImageURL().getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(productDto.getImageURL().getBytes());
        }

        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setName(productDto.getName());
        product.setImageURL(imagePath.resolve(productDto.getImageURL().getOriginalFilename()).toString());
        product.setCategoryId(category);
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {

        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(productDto.getImageURL().getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(productDto.getImageURL().getBytes());
        }

        Product product = productRepository.findById(productId).get();
        product.setDescription(productDto.getDescription());
        product.setImageURL(imagePath.resolve(productDto.getImageURL().getOriginalFilename()).toString());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }

    public Product getProductForUser(Product product) {

        return product;
    }

    public Product findById(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotExistsException("Product id is invalid: " + productId);
        }
        return optionalProduct.get();
    }
}
