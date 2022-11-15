package com.example.takaapi.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CategoryDto {

    @NotNull
    private String name;

//    private MultipartFile image;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public MultipartFile getImage() {
//        return image;
//    }
//
//    public void setImage(MultipartFile image) {
//        this.image = image;
//    }



}
