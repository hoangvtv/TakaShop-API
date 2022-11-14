package com.example.takaapi.controller;



import com.example.takaapi.common.ApiResponse;
import com.example.takaapi.dto.cart.AddToCartDto;
import com.example.takaapi.dto.cart.CartDto;
import com.example.takaapi.model.User;
import com.example.takaapi.repository.UserRepository;
import com.example.takaapi.security.CurrentUser;
import com.example.takaapi.security.UserPrincipal;
import com.example.takaapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;


    // post cart api
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@CurrentUser UserPrincipal currentUser, @RequestBody AddToCartDto addToCartDto
    ) {
        cartService.addToCart(currentUser, addToCartDto);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    // get all cart items for a user
    @GetMapping("/list")
    public ResponseEntity<CartDto> getCartItems(@CurrentUser UserPrincipal currentUser) {

        User user = userRepository.findById(currentUser.getId()).get();

        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    // delete a cart item for a user
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@CurrentUser UserPrincipal currentUser, @PathVariable("cartItemId") Integer itemId) {
        // find the user
        User user = userRepository.findById(currentUser.getId()).get();

        cartService.deleteCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}
