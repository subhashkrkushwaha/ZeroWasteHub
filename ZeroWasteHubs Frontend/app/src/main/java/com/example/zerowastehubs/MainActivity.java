package com.example.zerowastehubs;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zerowastehubs.Fragment.AccountFragment;
import com.example.zerowastehubs.Fragment.CategoriesFragment;
import com.example.zerowastehubs.Fragment.HomeFragment;
import com.example.zerowastehubs.Fragment.SearchFragment;
import com.example.zerowastehubs.Fragment.ShareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNv = findViewById(R.id.bottomNV);

        loadFragment(new HomeFragment(),true);
        bottomNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    loadFragment(new HomeFragment(),false);
                } else if (id == R.id.search) {
                    loadFragment(new SearchFragment(),false);
                }else if (id == R.id.share) {
                    loadFragment(new ShareFragment(),false);
                }else if (id == R.id.categories) {
                    loadFragment(new CategoriesFragment(),false);
                }else if (id == R.id.account) {
                    loadFragment(new AccountFragment(),false);
                }
                return true;
            }
        });
    }
    public void loadFragment(Fragment fragment, boolean addRemove){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(addRemove){
            ft.add(R.id.frameLayout,fragment);
        }else {
            ft.replace(R.id.frameLayout,fragment);
        }
        ft.commit();
    }
}