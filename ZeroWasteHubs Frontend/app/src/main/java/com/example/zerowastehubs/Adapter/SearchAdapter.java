package com.example.zerowastehubs.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zerowastehubs.Activity.ProductDetailsActivity;
import com.example.zerowastehubs.Model.AllProductModelH;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.ProductDto;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    Context context;
    List<ProductDto> itemList;
    private static String IMAGE_BASE_URL;;
//    private static final String IMAGE_BASE_URL = "http://10.46.214.169:8080/allImage/";

    public SearchAdapter(Context context, List<ProductDto> itemList) {
        this.context = context;
        this.itemList = itemList;
        String base = context.getString(R.string.ipConfig);
        IMAGE_BASE_URL = base + "allImage/";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.all_product_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductDto item = itemList.get(position);

        Glide.with(context)
                .load(IMAGE_BASE_URL + item.getImage())    // your API image path
                .placeholder(R.drawable.book) // optional
                .error(R.drawable.car)       // optional
                .into(holder.productImage);
        holder.productTitle.setText(item.getTitle());
        holder.productPrice.setText("₹ " + item.getPrice());
        holder.productLocation.setText(item.getLocation());
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Clicked: " + itemList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("price", item.getPrice());
            intent.putExtra("location", item.getLocation());
            intent.putExtra("details", item.getDetails());  // if available
            intent.putExtra("image", item.getImage());  // if available

            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return itemList != null ?  itemList.size() : 0;
    }
    // ✅ Called from Fragment when searching
    public void setFilteredList(List<ProductDto> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView productImage;
        TextView productTitle, productPrice, productLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productIcon);
            productTitle = itemView.findViewById(R.id.Title);
            productPrice = itemView.findViewById(R.id.Price);
            productLocation = itemView.findViewById(R.id.Location);
        }
    }
}
