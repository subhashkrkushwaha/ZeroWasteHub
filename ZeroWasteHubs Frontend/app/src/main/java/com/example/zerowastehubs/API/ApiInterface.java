package com.example.zerowastehubs.API;

import com.example.zerowastehubs.dto.CategoryDto;
import com.example.zerowastehubs.dto.ProductDto;
import com.example.zerowastehubs.dto.SubCategoryDto;
import com.example.zerowastehubs.dto.UserDto;
import com.example.zerowastehubs.dto.WishlistDto;
import com.example.zerowastehubs.dto.auth.LoginRequestDto;
import com.example.zerowastehubs.dto.auth.LoginResponseDto;
import com.example.zerowastehubs.dto.auth.UserRegisterDto;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {


//     Public API - Health check (no auth)
    @GET("auth/health-check")
    Call<String> healthCheck();
    //Authentication
    @POST("auth/user/register")
    Call<UserRegisterDto> signup(@Body UserRegisterDto userRegisterDto);
    @POST("auth/user/login")
    Call<LoginResponseDto> login(@Body LoginRequestDto loginRequest);
    //User
    @GET("/USER/get-id/{id}")
    Call<UserDto> userGetId(@Header("Authorization") String bearerToken,@Path("id") int userId);
    @GET("/USER/get-user-own")
    Call<UserDto> getUserOwn(@Header("Authorization") String bearerToken);
// get All user For Admin
    @GET("/USER/get-all")
    Call<List<UserDto>> getAllUser(@Header("Authorization")String bearerToken);
    // delete user coming soon

//        Category
    @POST("/category/save")
    Call<CategoryDto> saveCategory(@Header("Authorization") String bearerToken,@Body CategoryDto categoryDto);
    // update Category
    @PUT("/category/update")
    Call<CategoryDto> updateCategory(@Header("Authorization") String bearerToken,@Body CategoryDto categoryDto);
    //get all
    @GET("category/get-all")
    Call<List<CategoryDto>> getAllCategories(@Header("Authorization")String bearerToken);
    //get by id
    @GET("category/getById/{id}")
    Call<CategoryDto> getCategoryById(@Header("Authorization")String bearerToken,@Path("id") int id);
    //delete by id
    @DELETE("category/delete/{id}")
    Call<String> deleteById(@Header("Authorization")String bearerToken,@Path("id") int id);

//  Sub Categor
     //save
      @POST("sub-category/save")
      Call<SubCategoryDto>  saveSubCategory(@Header("Authorization")String bearerToken,@Body SubCategoryDto subCategoryDto);
      //update
      @PUT("sub-category/update/{id}")
      Call<SubCategoryDto>  updateSubCategory(@Header("Authorization")String bearerToken,@Path("id") int id, @Body SubCategoryDto subCategoryDto);
     // get-all category
     @GET("sub-category/get-all")
     Call<List<SubCategoryDto>>  getAllSubCategory(@Header("Authorization")String bearerToken);
     // get by id
    @GET("sub-category/getById/{id}")
    Call<SubCategoryDto> getById(@Header("Authorization")String bearerToken,@Path("id") int id);
    //delete by id
    @DELETE("sub-category/deleteById/{id}")
    Call<String> deleteByIdSC(@Header("Authorization")String bearerToken,@Path("id") int id);

//  Product
    @Multipart
    @POST("/SELLER/product/create-new")
    Call<ProductDto> createProduct(@Header("Authorization")String bearerToken,
                                   @Part("title") RequestBody title,
                                   @Part("details") RequestBody details,
                                   @Part("description") RequestBody description,
                                   @Part("location") RequestBody location,
                                   @Part("price") RequestBody price,
                                   @Part("categoryId") RequestBody categoryId,
                                   @Part("subCategoryId") RequestBody subCategoryId,
                                   @Part MultipartBody.Part imageo);
    @PUT("/SELLER/product/update/{id}")
    Call<ProductDto> updateProduct(@Header("Authorization")String bearerToken,@Path("id") int id ,@Body ProductDto productDto);
    @GET("/SELLER/product/all")
    Call<List<ProductDto>> getAllProduct(@Header("Authorization")String bearerToken);
    @GET("/SELLER/product/getById/{id}")
    Call<ProductDto> getProductById(@Header("Authorization")String bearerToken,@Path("id")int id);
    @DELETE("/SELLER/product/deleteBy/Id/{id}")
    Call<String> deleteProductById(@Header("Authorization")String bearerToken,@Path("id")int id);
//  Wishlist
    @POST("/USER/wishlist/add")
    Call<WishlistDto> addToWishlist(@Header("Authorization") String token, @Query("userId") int userId, @Query("productId") int productId);

    @DELETE("/USER/wishlist/remove") Call<String> removeFromWishlist(@Header("Authorization") String token, @Query("userId") int userId,
            @Query("productId") int productId
    );

}

