package com.example.zerowastehubs.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowastehubs.Adapter.SearchAdapter;
import com.example.zerowastehubs.Model.AllProductModelH;
import com.example.zerowastehubs.R;
import com.example.zerowastehubs.dto.CategoryDto;
import com.example.zerowastehubs.dto.ProductDto;
import com.example.zerowastehubs.repository.CategoryRepository;
import com.example.zerowastehubs.repository.ProductRepository;
import com.example.zerowastehubs.utitle.TokenManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView recyclerSItem;
    SearchView searchProduct;
    SearchAdapter adapter;
    List<ProductDto> productDtoList = new ArrayList<>();

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerSItem = view.findViewById(R.id.recyclerSItem);
        searchProduct = view.findViewById(R.id.searchProduct);

        recyclerSItem.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        adapter = new SearchAdapter(requireContext(), productDtoList);
        recyclerSItem.setAdapter(adapter);

        // 🔹 Get text from HomeFragment
        if (getArguments() != null) {
            String searchText = getArguments().getString("searchText");

            if (searchText != null && !searchText.isEmpty()) {
                searchProduct.setQuery(searchText, true); // auto filter
            }
        }
//search Screen
        searchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText); // ✅ IMPORTANT FIX
                return true;
            }
        });

        String token = TokenManager.getToken(requireContext());
        String bearerToken = "Bearer " + token;
        ProductRepository repo = new ProductRepository(requireContext());
        // categories
        repo.getAllProduct(bearerToken, new ProductRepository.ProductCallback() {
            @Override
            public void onSuccess(List<ProductDto> productDto) {
                productDtoList.clear();
                productDtoList.addAll(productDto);
                adapter.notifyDataSetChanged();
                Gson gson = new Gson();
                Log.e("All ProSFragment", gson.toJson(productDto));;
            }
            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void filterList(String text) {
        List<ProductDto> filteredList = new ArrayList<>();

        if (text.isEmpty()) {
            filteredList.addAll(productDtoList);
        } else {
            for (ProductDto item : productDtoList) {
                if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(requireContext(), "No product found", Toast.LENGTH_SHORT).show();
        }

        adapter.setFilteredList(filteredList);
    }
}
