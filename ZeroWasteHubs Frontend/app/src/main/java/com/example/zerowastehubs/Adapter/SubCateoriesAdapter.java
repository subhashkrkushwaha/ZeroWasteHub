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
import com.example.zerowastehubs.Model.SubCategory;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.SubCategoryDto;

import java.util.List;

public class SubCateoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    Context context;
    private List<SubCategoryDto> subCategoryList;
    private static String IMAGE_BASE_URL;
    public SubCateoriesAdapter(Context context, List<SubCategoryDto> subCategoryList){
        this.context = context;
        this.subCategoryList = subCategoryList;
        String base = context.getString(R.string.ipConfig);
        IMAGE_BASE_URL = base + "allImage/";
    }


    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categories_item_list,null);
//        ViewHolder viewHolder = new ViewHolder(view);
        return new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {

        // ✅ Get current item
        SubCategoryDto  subCategoryDto= subCategoryList.get(position);

        // ✅ Bind data to views
        holder.categoriesTitle.setText(subCategoryDto.getSubCategoryName());
        Glide.with(context)
                .load(IMAGE_BASE_URL + subCategoryDto.getImage())    // your API image path
                .placeholder(R.drawable.book) // optional
                .error(R.drawable.car)       // optional
                .into(holder.categoriesImage);

    }

    @Override
    public int getItemCount() {
        return subCategoryList != null ?subCategoryList.size() : 0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoriesTitle ;
        ImageView categoriesImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoriesTitle = itemView.findViewById(R.id.categoriesTitle);
            categoriesImage = itemView.findViewById(R.id.categoriesImage);
        }
    }

}
