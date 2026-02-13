package com.example.zerowastehubs.repository;

import android.content.Context;
import android.util.Log;

import com.example.zerowastehubs.API.ApiInterface;
import com.example.zerowastehubs.API.RetrofitClient;
import com.example.zerowastehubs.dto.UserDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private ApiInterface api;

    public interface UserCallback {
        void onSuccess(UserDto userDto);
        void onError(String message);
    }
    public  UserRepository(Context context){
        api = RetrofitClient.getRetrofitInstance(context).create(ApiInterface.class);
    }

    public void getUserOwn(String token, UserRepository.UserCallback callback) {
        Call<UserDto> call = api.getUserOwn(token);

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());

                    Log.e("SubCRepository", "SubCategories: ");

                } else {
                    callback.onError("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
