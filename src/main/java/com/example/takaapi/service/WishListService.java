package com.example.takaapi.service;

import com.example.takaapi.model.Product;
import com.example.takaapi.model.User;
import com.example.takaapi.model.WishList;
import com.example.takaapi.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    ProductService productService;

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<Product> getWishListForUser(User user) {
        final List<WishList> wishLists = wishListRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<Product> products = new ArrayList<>();
        for (WishList wishList: wishLists) {
            products.add(productService.getProductForUser(wishList.getProduct()));
        }

        return products;
    }
}
