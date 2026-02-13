package com.zerowastehub.backend.controller;

import com.zerowastehub.backend.dto.WishlistDto;
import com.zerowastehub.backend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/USER/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("add/{productId}")
    public WishlistDto addToWishlist(@PathVariable Integer productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        return wishlistService.addToWishlist(userEmail, productId);
    }

    @DeleteMapping("/remove/{productId}")
    public String removeFromWishlist(@PathVariable Integer productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        wishlistService.removeFromWishlist(userEmail, productId);
        return "Product removed from wishlist";
    }


}
