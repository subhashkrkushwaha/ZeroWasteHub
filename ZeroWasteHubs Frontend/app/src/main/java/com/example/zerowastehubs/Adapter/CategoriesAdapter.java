package com.example.zerowastehubs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.zerowastehubs.Model.Category;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.CategoryDto;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private Context context;
    private List<CategoryDto> categoriesList;
    private OnCategoryClickListener listener;
    private static String IMAGE_BASE_URL;
    // Constructor with Click Listener
    public CategoriesAdapter(Context context, List<CategoryDto> list, OnCategoryClickListener listener) {
        this.context = context;
        this.categoriesList = list;
        this.listener = listener;

        String base = context.getString(R.string.ipConfig);
        IMAGE_BASE_URL = base + "allImage/";

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.categories_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryDto categoryDto = categoriesList.get(position);

        holder.categoriesTitle.setText(categoryDto.getCategoryName());
        Glide.with(context)
                .load(IMAGE_BASE_URL + categoryDto.getImagePath())    // your API image path
                .placeholder(R.drawable.book) // optional
                .error(R.drawable.car)       // optional
                .into(holder.categoriesImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList != null ? categoriesList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoriesTitle;
        ImageView categoriesImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesTitle = itemView.findViewById(R.id.categoriesTitle);
            categoriesImage = itemView.findViewById(R.id.categoriesImage);
        }
    }

    // Callback Interface
    public interface OnCategoryClickListener {
        void onClick(int pos);
    }
}
