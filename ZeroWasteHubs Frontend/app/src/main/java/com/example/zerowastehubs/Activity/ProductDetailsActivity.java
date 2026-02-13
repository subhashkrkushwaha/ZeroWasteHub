package com.example.zerowastehubs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.zerowastehubs.R;

public class ProductDetailsActivity extends AppCompatActivity {
    ImageView productImage,productFavorite,productShare;
    TextView productPrice,productTitle,productDetails,productDescription;
    FrameLayout productLocation;
    AppCompatButton customerCall;
    private static final String IMAGE_BASE_URL = "http://10.46.214.169:8080/allImage/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productImage = findViewById(R.id.productImage);
        productFavorite = findViewById(R.id.productFavorite);
        productShare = findViewById(R.id.productShare);
        productPrice = findViewById(R.id.productPrice);
        productTitle = findViewById(R.id.productTitle);
        productDetails = findViewById(R.id.productDetails);
        productDescription = findViewById(R.id.productDescription);
        productLocation = findViewById(R.id.productLocation);
        customerCall = findViewById(R.id.customerCall);


        // RECEIVE DATA
        Intent intent = getIntent();
        String titleStr = intent.getStringExtra("title");
        String priceStr = String.valueOf(intent.getSerializableExtra("price"));
        String locationStr = intent.getStringExtra("location");
        String detailsStr = intent.getStringExtra("details");
        String descStr = intent.getStringExtra("description");
        String imageName = intent.getStringExtra("image");

        productTitle.setText(titleStr);
        productPrice.setText("₹"+priceStr);
        productDetails.setText(Html.fromHtml("<b>Detail : </b> " + detailsStr));
        productDescription.setText(Html.fromHtml("<b>Description : </b>"+descStr));

// LOAD IMAGE
        Glide.with(this)
                .load(IMAGE_BASE_URL + imageName)
                .placeholder(R.drawable.book)
                .error(R.drawable.car)
                .into(productImage);


        customerCall.setOnClickListener(v -> {
            Toast.makeText(this, "Contact is develop", Toast.LENGTH_SHORT).show();
        });
        productFavorite.setOnClickListener(v -> {
            Toast.makeText(this, "Develop Mode", Toast.LENGTH_SHORT).show();
        });
        productShare.setOnClickListener(v -> {
            Toast.makeText(this, "Develop Mode", Toast.LENGTH_SHORT).show();
        });
    }
}