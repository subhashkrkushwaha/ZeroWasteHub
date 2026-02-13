package com.example.zerowastehubs;

import android.content.Context;

import com.example.zerowastehubs.Model.CategoriesModelH;
import com.example.zerowastehubs.Model.Category;
import com.example.zerowastehubs.R;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    // TEMPORARY LOCAL DATA (will replace with API/DB later)
    public static List<Category> localCategories() {
        List<Category> categories_item = new ArrayList<>();

        categories_item.add(new Category("Mobile & Accessories", R.drawable.phone));
        categories_item.add(new Category("Electronics & Appliances", R.drawable.phone));
        categories_item.add(new Category("Fashion", R.drawable.cloths));
        categories_item.add(new Category("Beauty & Personal Care", R.drawable.cloths));
        categories_item.add(new Category("Home & Kitchen", R.drawable.cloths));
        categories_item.add(new Category("Furniture", R.drawable.cloths));
        categories_item.add(new Category("Grocery & Essentials", R.drawable.cloths));
        categories_item.add(new Category("Books & Stationery", R.drawable.book));
        categories_item.add(new Category("Kids & Toys",R.drawable.cloths));
        categories_item.add(new Category("Sports & Fitness",R.drawable.cloths));
        categories_item.add(new Category("Automotive", R.drawable.cloths));
        categories_item.add(new Category("Shoes & Footwear", R.drawable.shoes));
        categories_item.add(new Category("Services", R.drawable.cloths));
        categories_item.add(new Category("Other", R.drawable.cloths));


        return categories_item;
    }

    // FUTURE: This will replace local categories with real API/database
    public static List<Category> getCategories(Context context) {
        // TODO: Add Retrofit API call here
        return localCategories();   // for now
    }
}
