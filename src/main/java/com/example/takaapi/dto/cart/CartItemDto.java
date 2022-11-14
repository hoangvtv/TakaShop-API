package com.example.takaapi.dto.cart;





import com.example.takaapi.model.Cart;
import com.example.takaapi.model.Product;

import javax.validation.constraints.NotNull;

public class CartItemDto {

    private Integer id;

    @NotNull
    private Integer quantity;
    @NotNull
    private Product product;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.id = cart.getCartId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
