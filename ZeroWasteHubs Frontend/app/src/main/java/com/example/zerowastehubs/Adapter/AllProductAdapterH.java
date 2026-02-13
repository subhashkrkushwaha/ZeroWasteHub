package com.example.zerowastehubs.Adapter;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.zerowastehubs.Activity.ProductDetailsActivity;
import com.example.zerowastehubs.Model.AllProductModelH;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.ProductDto;

import java.util.List;

public class AllProductAdapterH extends RecyclerView.Adapter<AllProductAdapterH.ViewHolder>{
    Context context;
    List<ProductDto> allProductitem;
    private static String IMAGE_BASE_URL;
//    private static final String IMAGE_BASE_URL = "http://10.46.214.169:8080/allImage/";
    public AllProductAdapterH(Context context, List<ProductDto> allProductitem){
           this.context= context;
           this.allProductitem = allProductitem;

        String base = context.getString(R.string.ipConfig);
         IMAGE_BASE_URL = base + "allImage/";
       }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup prants, int viewType){

        View view = LayoutInflater.from(context).inflate(R.layout.all_product_recyclerview,null);
        return  new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ProductDto allModel = allProductitem.get(position);

        holder.price.setText(String.valueOf("₹ " +allModel.getPrice()));
        holder.title.setText(allModel.getTitle());
        holder.location.setText(allModel.getLocation());
        Glide.with(context)
                .load(IMAGE_BASE_URL + allModel.getImage())    // your API image path
                .placeholder(R.drawable.book) // optional
                .error(R.drawable.car)       // optional
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Clicked: " + allProductitem.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("title", allModel.getTitle());
            intent.putExtra("price", allModel.getPrice());
//            intent.putExtra("price", String.valueOf(allModel.getPrice()));
            intent.putExtra("location", allModel.getLocation());
            intent.putExtra("details", allModel.getDetails());  // if available
            intent.putExtra("description",allModel.getDescription());
            intent.putExtra("image", allModel.getImage());  // if available

            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount(){
        return allProductitem != null ? allProductitem.size() :0;
    }

    public static class ViewHolder  extends RecyclerView.ViewHolder {
        TextView price,title,location;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.Price);
            title = itemView.findViewById(R.id.Title);
            location = itemView.findViewById(R.id.Location);
            imageView = itemView.findViewById(R.id.productIcon);
        }
    }
}
