package com.example.takaapi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column
    @Min(value = 6, message = "Name must be more than 6 characters")
    private String name;

//    @Column
//    @OneToOne(cascade = CascadeType.ALL) //khi xóa một dòng dữ liệu trong table image thì dữ liệu bên category cũng bị xóa
//    @JoinColumn(name = "image_id", referencedColumnName = "id")
//    private String image;


    @OneToMany(mappedBy = "categoryId")
    @JsonIgnoreProperties(value = "category")
    private List<Product> products;



    public Category() {
    }

    public Category(String name, List<Product> products) {
        this.name = name;

        this.products = products;
    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
