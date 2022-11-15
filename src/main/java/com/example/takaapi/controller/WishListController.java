package com.example.takaapi.controller;



import com.example.takaapi.common.ApiResponse;
import com.example.takaapi.model.Product;
import com.example.takaapi.model.User;
import com.example.takaapi.model.WishList;
import com.example.takaapi.repository.UserRepository;
import com.example.takaapi.security.CurrentUser;
import com.example.takaapi.security.UserPrincipal;
import com.example.takaapi.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    UserRepository userRepository;


    // save product as wishlist item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@CurrentUser UserPrincipal currentUser, @RequestBody Product product) {

        User user = userRepository.findById(currentUser.getId()).get();

        WishList wishList = new WishList(user, product);

        wishListService.createWishlist(wishList);

        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }


    // get all wishlist item for a user
    @GetMapping("/list")
    public ResponseEntity<List<Product>> getWishList(@CurrentUser UserPrincipal currentUser) {

        User user = userRepository.findById(currentUser.getId()).get();

        List<Product> products = wishListService.getWishListForUser(user);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
