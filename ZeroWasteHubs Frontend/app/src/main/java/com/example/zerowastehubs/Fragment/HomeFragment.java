package com.example.zerowastehubs.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zerowastehubs.API.ApiInterface;
import com.example.zerowastehubs.API.RetrofitClient;
import com.example.zerowastehubs.Activity.LocationActivity;
import com.example.zerowastehubs.Activity.NotificationsActivity;
import com.example.zerowastehubs.Activity.WatchListActivity;
import com.example.zerowastehubs.Adapter.AllProductAdapterH;
import com.example.zerowastehubs.Adapter.CategoriesAdapterH;
import com.example.zerowastehubs.Adapter.RecentViewAdapter;
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

import retrofit2.Call;


public class HomeFragment extends Fragment {

    SearchView searchView ;
    ImageView watchList ,notification ;
    TextView location ;
    RecyclerView recyclerCategories,recyclerRecent,recycler_AllProduct;
    private ActivityResultLauncher<Intent> locationLauncher;

    List<CategoryDto> categoryDtoList = new ArrayList();
    List<ProductDto> productDtoList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_home, container, false);
         searchView = view.findViewById(R.id.searchView);
         watchList = view.findViewById(R.id.watchList);
         notification = view.findViewById(R.id.notification);
         location = view.findViewById(R.id.location);
        recyclerCategories = view.findViewById(R.id.recyclerCategories);
        recyclerRecent = view.findViewById(R.id.recyclerRecent);
        recycler_AllProduct = view.findViewById(R.id.recycler_AllProduct);

        recyclerCategories.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL,false));
        String token = TokenManager.getToken(requireContext());
        String bearerToken = "Bearer " + token;

        CategoriesAdapterH categoriesAdapterH = new CategoriesAdapterH(requireContext(), categoryDtoList,
                (category, position) -> {
                    Fragment fragment = new CategoriesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("selected_index", position);
                    fragment.setArguments(bundle);
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .addToBackStack(null)
                            .commit();
                });
        recyclerCategories.setAdapter(categoriesAdapterH);

//       Recently Click view
        recyclerRecent.setLayoutManager(new LinearLayoutManager(requireContext(),
                             LinearLayoutManager.HORIZONTAL,false));
        List<AllProductModelH> recentModel= new ArrayList<>();
        recentModel.add(new AllProductModelH("Food",R.drawable.pizza));
        recentModel.add(new AllProductModelH("Clothes",R.drawable.cloths));
        recentModel.add(new AllProductModelH("Electronic",R.drawable.phone));
        recentModel.add(new AllProductModelH("Book",R.drawable.book));
        recentModel.add(new AllProductModelH("Book",R.drawable.pizza));
        recentModel.add(new AllProductModelH("Book",R.drawable.pizza));
        RecentViewAdapter recentAdapter = new RecentViewAdapter(requireContext(),recentModel);
        recyclerRecent.setAdapter(recentAdapter);


//        Fresh Recommendations all product
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recycler_AllProduct.setLayoutManager(gridLayoutManager);

        AllProductAdapterH allProductAdapterH = new AllProductAdapterH(requireContext(),productDtoList);
        recycler_AllProduct.setAdapter(allProductAdapterH);

        ProductRepository frepo = new ProductRepository(requireContext());

        frepo.getAllProduct(bearerToken, new ProductRepository.ProductCallback() {
            @Override
            public void onSuccess(List<ProductDto> productDto) {
                productDtoList.clear();
                productDtoList.addAll(productDto);
                allProductAdapterH.notifyDataSetChanged();
                Gson gson = new Gson();
                Log.e("All Product SFragment", gson.toJson(productDto));
            }
            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

     /*Search View in home page*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openSearchFragment(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    openSearchFragment(newText);
                }
                return true;
            }
        });

        /*Location   Home page*/
        location.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LocationActivity.class);
            locationLauncher.launch(intent);
        });
        /*For get location by user*/
        locationLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String address = result.getData().getStringExtra("address");
                        location.setText(address);
                    }
                });

        /*Wishlist  Home page*/
        watchList.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(),WatchListActivity.class));
        });
        /*Notification   Home page*/
        notification.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(),NotificationsActivity.class));
        });

        // categories
        CategoryRepository repo = new CategoryRepository(requireContext());

        repo.getCategories(bearerToken, new CategoryRepository.CategoryCallback() {
            @Override
            public void onSuccess(List<CategoryDto> categories) {
                categoryDtoList.clear();
                categoryDtoList.addAll(categories);
                recyclerCategories.getAdapter().notifyDataSetChanged();
                Gson gson = new Gson();
                    Log.i("All Categories HFragment", gson.toJson(categories));
            }
            @Override
            public void onError(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
         return  view;
    }
    //search
    private void openSearchFragment(String text) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchText", text);
        searchFragment.setArguments(bundle);
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, searchFragment)
                .addToBackStack(null)
                .commit();
    }
}