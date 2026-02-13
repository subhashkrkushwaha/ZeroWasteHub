package com.example.zerowastehubs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zerowastehubs.API.ApiInterface;
import com.example.zerowastehubs.API.RetrofitClient;
import com.example.zerowastehubs.MainActivity;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.auth.LoginRequestDto;
import com.example.zerowastehubs.dto.auth.LoginResponseDto;
import com.example.zerowastehubs.utitle.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingInActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds delay

    AppCompatButton buttonSignIn;
    EditText userName,userPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sing_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        buttonSignIn = findViewById(R.id.buttonSignIn);
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        TextView textSignUp = findViewById(R.id.textSignUpB);

        buttonSignIn.setOnClickListener(v -> login());

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingInActivity.this,SingUpActivity.class);
                startActivity(intent);
            }
        });

//        new Handler().postDelayed(() -> {
//            String token = TokenManager.getToken(SingInActivity.this);
//            if (token != null && !token.isEmpty()) {
//                // ✅ Token exists, navigate to MainActivity
//                startActivity(new Intent(SingInActivity.this, MainActivity.class));
//            } else {
//                // 🔐 No token, navigate to LoginActivity
//                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
//            }
//            finish();
//        }, SPLASH_DELAY);
           String token = TokenManager.getToken(SingInActivity.this);
           if (token != null && !token.isEmpty()) {
                  //  Token exists, navigate to MainActivity
                 startActivity(new Intent(SingInActivity.this, MainActivity.class));
           }

    }
    private void login(){
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance(SingInActivity.this).create(ApiInterface.class);
        String userN = userName.getText().toString();
        String userP = userPassword.getText().toString();
        // validation
          if(userN.isEmpty()){
            userName.setError("User Email required");
            userName.requestFocus();
            return;
          }
          if(userP.isEmpty()){
              userPassword.setError("Password is require");
              userPassword.requestFocus();
              return;
          }
        LoginRequestDto loginRequestDto = new LoginRequestDto(userN,userP);
          apiInterface.login(loginRequestDto).enqueue(new Callback<LoginResponseDto>() {
              @Override
              public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                 if(response.code() == 200 && response != null){
                     Toast.makeText(SingInActivity.this, "Login  Successful", Toast.LENGTH_SHORT).show();
                     TokenManager.saveToken(SingInActivity.this,response.body().getToken());
                    startActivity(new Intent(SingInActivity.this, MainActivity.class));
                    finish();
                 }
              }
              @Override
              public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                  Toast.makeText(SingInActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                  Log.e("Login fail",t.getMessage());
              }
          });
    }
}