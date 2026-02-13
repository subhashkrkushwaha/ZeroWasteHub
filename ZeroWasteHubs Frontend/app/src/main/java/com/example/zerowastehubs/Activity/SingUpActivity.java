package com.example.zerowastehubs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.auth.UserRegisterDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SingUpActivity extends AppCompatActivity {

    EditText userName,userEmail,userNumber,userPassword,userConfirmPassword;
    AppCompatButton buttonSignUp;
    TextView textSignInB;
    String userN , email,number ,password , comPassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sing_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
         textSignInB = findViewById(R.id.textSignInB);
         buttonSignUp = findViewById(R.id.buttonSignUp);
         userName = findViewById(R.id.userName);
         userEmail = findViewById(R.id.userEmail);
         userNumber = findViewById(R.id.userNumber);
         userPassword = findViewById(R.id.userPassword);
         userConfirmPassword = findViewById(R.id.userConfirmPassword);

        buttonSignUp.setOnClickListener(v -> signupUser());

//        buttonSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SingUpActivity.this,SingInActivity.class);
//                startActivity(intent);
//            }
//        });

        textSignInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingUpActivity.this,SingInActivity.class);
                startActivity(intent);
            }
        });
    }
    private void signupUser() {
        userN = userName.getText().toString();
        email = userEmail.getText().toString();
        number = userNumber.getText().toString();
        password = userPassword.getText().toString();
        comPassword = userConfirmPassword.getText().toString();
        // Validation
        if (userN.isEmpty() || email.isEmpty() || number.isEmpty() ||
                password.isEmpty() || comPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(comPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance(SingUpActivity.this).create(ApiInterface.class);
        UserRegisterDto userRegisterDto = new UserRegisterDto(userN,email,number,password);

        apiInterface.signup(userRegisterDto).enqueue(new Callback<UserRegisterDto>() {
            @Override
            public void onResponse(Call<UserRegisterDto> call, Response<UserRegisterDto> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String msg = response.body().getMessage();
                    if (msg == null || msg.trim().isEmpty()) {
                        msg = "Signup successful";
                    }
                    Toast.makeText(SingUpActivity.this, msg, Toast.LENGTH_SHORT).show();
                    finish(); // Go back to login
                } else {
                    Toast.makeText(SingUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserRegisterDto> call, Throwable t) {
                Toast.makeText(SingUpActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("singup",t.getMessage());
            }
        });
    }
}