package com.example.zerowastehubs.repository;

import android.content.Context;
import android.util.Log;

import com.example.zerowastehubs.API.ApiInterface;
import com.example.zerowastehubs.API.RetrofitClient;
import com.example.zerowastehubs.dto.CategoryDto;
import com.example.zerowastehubs.dto.SubCategoryDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    private ApiInterface api;

    public interface CategoryCallback {
        void onSuccess(List<CategoryDto> categories);
        void onError(String message);
    }

    public interface SubCategoryCallback {
        void onSuccess(List<SubCategoryDto> subCategories);
        void onError(String message);
    }

    public CategoryRepository(Context context) {
        api = RetrofitClient.getRetrofitInstance(context).create(ApiInterface.class);
    }

    public void getCategories(String token, CategoryCallback callback) {
        Call<List<CategoryDto>> call = api.getAllCategories(token);

        call.enqueue(new Callback<List<CategoryDto>>() {
            @Override
            public void onResponse(Call<List<CategoryDto>> call, Response<List<CategoryDto>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());

                    List<CategoryDto> list = response.body();
                    callback.onSuccess(list);
                    Log.e("SubCRepository", "SubCategories: " + list.toString());

                } else {
                    callback.onError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CategoryDto>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void getSubCategories(String token, SubCategoryCallback callback) {
        Call<List<SubCategoryDto>> call = api.getAllSubCategory(token);

        call.enqueue(new Callback<List<SubCategoryDto>>() {
            @Override
            public void onResponse(Call<List<SubCategoryDto>> call, Response<List<SubCategoryDto>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());

                    List<SubCategoryDto> list = response.body();
                    callback.onSuccess(list);
                    Log.e("SubCRepository", "SubCategories: " + list.toString());

                } else {
                    callback.onError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<SubCategoryDto>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
