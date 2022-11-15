package com.example.takaapi.controller;



import com.example.takaapi.common.ApiResponse;
import com.example.takaapi.dto.CategoryDto;
import com.example.takaapi.model.Category;
import com.example.takaapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    CategoryService categoryService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDto categoryDto)  {

        try {
            ValidatorFactory valFac = Validation.buildDefaultValidatorFactory();
            Validator val = valFac.getValidator();
            Set<ConstraintViolation<CategoryDto>> validations = val.validate(categoryDto);
            for (ConstraintViolation<CategoryDto> validation : validations) {
                return new ResponseEntity<>(new ApiResponse(false, validation.getMessage()), HttpStatus.BAD_REQUEST);
            }

            categoryService.createCategory(categoryDto);
            return new ResponseEntity<>(new ApiResponse(true, "A new category has been created successfully"), HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(new ApiResponse(false, ex.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Category>> getListCategory() {

        return new ResponseEntity<>(categoryService.getListCategory(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> editCategory(@PathParam("id") int id, @RequestBody CategoryDto categoryDto) throws IOException {

        if (!categoryService.findById(id)) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exists"), HttpStatus.NOT_FOUND);
        }

        categoryService.editCategory(id, categoryDto);

        return new ResponseEntity<>(new ApiResponse(true, "Category has been updated successfully"), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathParam("id") int id) {
        if (!categoryService.findById(id)) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exists"), HttpStatus.NOT_FOUND);
        }

        categoryService.deleteCategory(id);

        return new ResponseEntity<>(new ApiResponse(true, "Category has been deleted successfully"), HttpStatus.OK);

    }


}
