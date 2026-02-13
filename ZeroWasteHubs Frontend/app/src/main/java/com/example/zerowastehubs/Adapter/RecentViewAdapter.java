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

import com.example.zerowastehubs.Activity.ProductDetailsActivity;
import com.example.zerowastehubs.Model.AllProductModelH;
import com.example.zerowastehubs.Model.Category;
import com.example.zerowastehubs.OnCategoryClick;
import com.example.zerowastehubs.R;

import java.util.List;

public class RecentViewAdapter extends RecyclerView.Adapter<RecentViewAdapter.ViewHolder> {
    Context context;
    private List<AllProductModelH> categorieslist;
    OnCategoryClick listener;
    public RecentViewAdapter(Context context, List<AllProductModelH> categorieslist){
        this.context= context;
        this.categorieslist =  categorieslist;
    }

    @NonNull
    @Override
    public RecentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view =    LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recentview, parent, false);

        return new RecentViewAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecentViewAdapter.ViewHolder holder, int position) {
        AllProductModelH allModel =  categorieslist.get(position);
        holder.title.setText(allModel.getTitle());
        holder.productImage.setImageResource(allModel.getImage());
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Clicked: " +  categorieslist.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra("title", allModel.getTitle());
            intent.putExtra("price", allModel.getPrice());
            intent.putExtra("location", allModel.getLocation());
            intent.putExtra("details", allModel.getDetails());  // if available
//            intent.putExtra("image", allModel.getImageUrl());  // if available
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return categorieslist == null ? 0 : categorieslist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        AppCompatImageView productImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            productImage = itemView.findViewById(R.id.productIcon);
        }
    }
}
