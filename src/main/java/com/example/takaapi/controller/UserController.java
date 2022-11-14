package com.example.takaapi.controller;


import com.example.takaapi.common.ApiResponse;
import com.example.takaapi.exception.ResourceNotFoundException;
import com.example.takaapi.model.User;
import com.example.takaapi.payload.PasswordRequest;
import com.example.takaapi.payload.UserIdentityAvailability;
import com.example.takaapi.payload.UserProfile;
import com.example.takaapi.payload.UserSummary;
import com.example.takaapi.repository.UserRepository;
import com.example.takaapi.security.CurrentUser;
import com.example.takaapi.security.UserPrincipal;
import com.example.takaapi.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private CartService cartService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));


        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(),
                user.getCreatedAt(), cartService.listCartItems(user));

        return userProfile;
    }

    @PutMapping("/users/changePassword/{username}")
    public ResponseEntity<?> changePassword(@PathVariable(value = "username") String username, @RequestBody PasswordRequest passwordRequest) {

        if (!passwordRequest.getPasswordNew().matches("^(?:(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*).{8}$")) {

            return new ResponseEntity(new ApiResponse(false, "Password must have at least: 1 Lowercase," +
                    " 1 Uppercase, and Digits!"),
                    HttpStatus.BAD_REQUEST);
        }


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));


        if (passwordEncoder.matches(passwordRequest.getPasswordOld(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordRequest.getPasswordNew()));
            userRepository.save(user);
            return new ResponseEntity(new ApiResponse(true, "Password has been updated"),
                    HttpStatus.OK);

        } else {
            return new ResponseEntity(new ApiResponse(false, "Wrong old password"),
                    HttpStatus.BAD_REQUEST);
        }

    }


}
