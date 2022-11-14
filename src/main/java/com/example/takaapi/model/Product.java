package com.example.takaapi.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private double price;

//    @Column
//    @NotNull
//    private Set<Character> sizes= new HashSet<>();

    private  String imageURL;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", foreignKey = @ForeignKey(name = "fk_product_category"))
    @JsonIgnoreProperties(value = "products")
    private Category categoryId;


    public Product(String name, double price, String imageURL, Category categoryId, String description) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
        this.categoryId = categoryId;
        this.description = description;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    @Column
    private String description;


    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
