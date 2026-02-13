package com.zerowastehub.backend.service;

import com.zerowastehub.backend.ExceptionHandling.GlobalExceptionHandler;
import com.zerowastehub.backend.ExceptionHandling.ResourceNotFoundException;
import com.zerowastehub.backend.dto.WishlistDto;
import com.zerowastehub.backend.entity.Product;
import com.zerowastehub.backend.entity.User;
import com.zerowastehub.backend.entity.Wishlist;
import com.zerowastehub.backend.repository.UserRepository;
import com.zerowastehub.backend.repository.ProductRepository;
import com.zerowastehub.backend.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    //  ADD TO WISHLIST
    @Transactional
    public WishlistDto addToWishlist(String userEmail, Integer productId) {
        // find user
        User user = userService.getEntityByUserEamil(userEmail);
        Integer userId =user.getId();
        // Prevent duplicate wishlist entry
        wishlistRepository.findByUserIdAndProductId(userId, productId)
                .ifPresent(w -> {
                    throw new GlobalExceptionHandler.AlreadyExistsException("Product already in wishlist");
                });
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setCreatedAt(LocalDateTime.now());

        Wishlist saved = wishlistRepository.save(wishlist);

        return  modelMapper.map(saved, WishlistDto.class);
    }
    // ✅ REMOVE FROM WISHLIST
    @Transactional
    public void removeFromWishlist(String userEmail, Integer productId) {
        User user = userService.getEntityByUserEamil(userEmail);
        Integer userId =user.getId();
        Wishlist wishlist = wishlistRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in wishlist"));

        wishlistRepository.delete(wishlist);
    }

}
