package com.example.takaapi.service;



import com.example.takaapi.dto.cart.AddToCartDto;
import com.example.takaapi.dto.cart.CartDto;
import com.example.takaapi.dto.cart.CartItemDto;
import com.example.takaapi.exception.CustomException;
import com.example.takaapi.model.Cart;
import com.example.takaapi.model.Product;
import com.example.takaapi.model.User;
import com.example.takaapi.repository.CartRepository;
import com.example.takaapi.repository.UserRepository;
import com.example.takaapi.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
//    @Autowired
//    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    public void addToCart(UserPrincipal currentUser, AddToCartDto addToCartDto) {

        // validate if the product id is valid
//        Product product = productService.findById(addToCartDto.getProductId());

        User user = userRepository.findById(currentUser.getId()).get();
        Cart cart = new Cart();
//        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        // save the cart
        cartRepository.save(cart);

    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto= new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        // the item id belongs to user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()) {
            throw new CustomException("Cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CustomException("Cart item does not belong to user: " + cartItemId);
        }

        cartRepository.delete(cart);
    }
}
