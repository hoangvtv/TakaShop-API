package com.example.takaapi.controller;



import com.example.takaapi.common.ApiResponse;
import com.example.takaapi.dto.ProductDto;
import com.example.takaapi.model.Category;
import com.example.takaapi.model.Product;
import com.example.takaapi.repository.CategoryRepository;
import com.example.takaapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepository categoryRepository;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exists"), HttpStatus.NOT_FOUND);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    // create an api to edit the product
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productRequest) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(productRequest.getCategoryId());
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exists"), HttpStatus.NOT_FOUND);
        }

        Product product = productService.findById(productId);
        if (product == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Product does not exists"), HttpStatus.NOT_FOUND);
        }

        productService.updateProduct(productRequest, productId);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }


}
