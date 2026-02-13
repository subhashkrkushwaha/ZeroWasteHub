package com.example.zerowastehubs.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.zerowastehubs.API.ApiInterface;
import com.example.zerowastehubs.API.RetrofitClient;
import com.example.zerowastehubs.Adapter.CategoriesAdapter;
import com.example.zerowastehubs.Adapter.SubCateoriesAdapter;
import com.example.zerowastehubs.Model.Category;
import com.example.zerowastehubs.Model.SubCategory;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.CategoryDto;
import com.example.zerowastehubs.dto.SubCategoryDto;
import com.example.zerowastehubs.repository.CategoryRepository;
import com.example.zerowastehubs.utitle.TokenManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class CategoriesFragment extends Fragment {

    RecyclerView rvCategories, subCatRigSide;
    FrameLayout rightContainer;
    List<CategoryDto> categoryDtoLis = new ArrayList<>();
    List<SubCategoryDto> subCategoryDtoLis = new ArrayList<>();
    List<SubCategoryDto> subCategoryFilteredList = new ArrayList<>();

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        rvCategories = view.findViewById(R.id.rvCategories);
        subCatRigSide = view.findViewById(R.id.subCatRigSide);
        rightContainer = view.findViewById(R.id.rightContainer);

        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getContext(), categoryDtoLis, pos -> {
                    int categoryId = categoryDtoLis.get(pos).getId();
                    loadSubCategories(categoryId);
                });
        rvCategories.setAdapter(categoriesAdapter);
        subCatRigSide.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // 🔑 Replace this with your JWT token from Login
        String token = TokenManager.getToken(requireContext());
        String bearerToken = "Bearer " + token;

        CategoryRepository repo = new CategoryRepository(requireContext());

        // categories
        repo.getCategories(bearerToken, new CategoryRepository.CategoryCallback() {
            @Override
            public void onSuccess(List<CategoryDto> categories) {
                categoryDtoLis.clear();
                categoryDtoLis.addAll(categories);
                rvCategories.getAdapter().notifyDataSetChanged();
                // load default (first) category
                if (!categoryDtoLis.isEmpty()) {
                    loadSubCategories(categoryDtoLis.get(0).getId());
                }

                Gson gson = new Gson();
                Log.e("All Categories CFragment", gson.toJson(categoryDtoLis));;

            }
            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        // subCategories
        repo.getSubCategories(bearerToken, new CategoryRepository.SubCategoryCallback() {
            @Override
            public void onSuccess(List<SubCategoryDto> subCategories) {
                subCategoryDtoLis.clear();
                subCategoryDtoLis.addAll(subCategories);
                // default load first category
                if (!categoryDtoLis.isEmpty()) {
                    loadSubCategories(categoryDtoLis.get(0).getId());
                }
                Gson gson = new Gson();
                Log.e("All SubCategories CFragment ", gson.toJson(subCategoryDtoLis));
            }
            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    private void loadSubCategories(int categoryId) {
        subCategoryFilteredList.clear();
        for (SubCategoryDto dto : subCategoryDtoLis) {
            if (dto.getCategoryId() == categoryId) {
                subCategoryFilteredList.add(dto);
            }
        }
        SubCateoriesAdapter adapter = new SubCateoriesAdapter(getContext(), subCategoryFilteredList);
        subCatRigSide.setAdapter(adapter);
    }
}