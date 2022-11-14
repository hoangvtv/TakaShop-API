package com.example.takaapi.payload;



import com.example.takaapi.dto.cart.CartDto;

import java.time.Instant;

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    private CartDto carts;



    public UserProfile(Long id, String username, String name, Instant joinedAt, CartDto carts) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
        this.carts=carts;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public CartDto getCarts() {
        return carts;
    }

    public void setCarts(CartDto carts) {
        this.carts = carts;
    }
}
