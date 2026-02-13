package com.example.zerowastehubs.repository;

import android.content.Context;

import com.example.zerowastehubs.API.ApiInterface;
import com.example.zerowastehubs.API.RetrofitClient;
import com.example.zerowastehubs.dto.ProductDto;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ProductRepository {

    private ApiInterface api;

    // ============================
    // CALLBACK INTERFACES
    // ============================
    public interface ProductCallback {
        void onSuccess(List<ProductDto> productDtoList);
        void onError(String message);
    }

    public interface CreateProductCallback {
        void onSuccess(ProductDto productDto);
        void onError(String message);
    }

    public ProductRepository(Context context) {
        api = RetrofitClient.getRetrofitInstance(context).create(ApiInterface.class);
    }

    // ============================
    // GET ALL PRODUCTS
    // ============================
    public void getAllProduct(String token, ProductCallback callback) {
        Call<List<ProductDto>> call = api.getAllProduct(token);

        call.enqueue(new Callback<List<ProductDto>>() {
            @Override
            public void onResponse(Call<List<ProductDto>> call, Response<List<ProductDto>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ProductDto>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // ============================
    // CREATE PRODUCT WITH IMAGE
    // ============================
    public void createProduct(String token, String title, String details, String description, String location, String price,
            String categoryId,
            String subCategoryId,
            File imageFile,
            CreateProductCallback callback
    ) {

        // Convert text fields
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody detailsBody = RequestBody.create(MediaType.parse("text/plain"), details);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody locBody = RequestBody.create(MediaType.parse("text/plain"), location);
        RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), price);
        RequestBody catBody = RequestBody.create(MediaType.parse("text/plain"), categoryId);
        RequestBody subCatBody = RequestBody.create(MediaType.parse("text/plain"), subCategoryId);

        // Convert file to Multipart
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part imagePart =
                MultipartBody.Part.createFormData("image", imageFile.getName(),fileBody);

        // Call API
        Call<ProductDto> call = api.createProduct(token, titleBody, detailsBody, descBody, locBody, priceBody, catBody,
                subCatBody,
                imagePart
        );

        call.enqueue(new Callback<ProductDto>() {
            @Override
            public void onResponse(Call<ProductDto> call, Response<ProductDto> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProductDto> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
