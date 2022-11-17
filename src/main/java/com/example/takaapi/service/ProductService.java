package com.example.takaapi.service;


import com.example.takaapi.exception.ProductNotExistsException;
import com.example.takaapi.model.Category;
import com.example.takaapi.model.Product;
import com.example.takaapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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


    public void createProduct(String name, double price, String description, MultipartFile imageURL, Category category) throws IOException {
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(imageURL.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(imageURL.getBytes());
        }

        Product product = new Product();
        product.setDescription(description);
        product.setName(name);
        product.setImageURL(imagePath.resolve(imageURL.getOriginalFilename()).toString());
        product.setCategoryId(category);
        product.setPrice(price);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void updateProduct(String name, double price, String description, MultipartFile imageURL, Category category, Integer productId) throws Exception {

        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(imageURL.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(imageURL.getBytes());
        }

        Product product = productRepository.findById(productId).get();
        product.setDescription(description);
        product.setImageURL(imagePath.resolve(imageURL.getOriginalFilename()).toString());
        product.setName(name);
        product.setPrice(price);
        product.setCategoryId(category);
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
