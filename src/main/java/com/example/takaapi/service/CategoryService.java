package com.example.takaapi.service;




import com.example.takaapi.dto.CategoryDto;
import com.example.takaapi.model.Category;
import com.example.takaapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.List;


@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;




    public void createCategory(CategoryDto categoryDto) throws IOException {


        Category category= new Category();
        category.setName(categoryDto.getName());

        categoryRepository.save(category);
    }


    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }

    public void editCategory(int id,CategoryDto categoryRequest) throws IOException {

        Category category= categoryRepository.findById(id).get();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
    }


    public boolean findById(int id) {
        return categoryRepository.findById(id).isPresent();
    }


    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }
}
