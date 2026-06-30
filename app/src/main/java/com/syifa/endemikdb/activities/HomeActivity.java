package com.syifa.endemikdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.syifa.endemikdb.R;
import com.syifa.endemikdb.fragments.HewanFragment;
import com.syifa.endemikdb.fragments.TumbuhanFragment;
import com.syifa.endemikdb.utils.ThemeManager;

public class HomeActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        if (savedInstanceState == null) {
            loadFragment(new HewanFragment());
        }

        bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_hewan) {
                loadFragment(new HewanFragment());
                return true;
            }

            if (item.getItemId() == R.id.nav_tumbuhan) {
                loadFragment(new TumbuhanFragment());
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        int color = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
                ? getColor(android.R.color.white)
                : getColor(android.R.color.black);

        toolbar.post(() -> {
            if (toolbar.getOverflowIcon() != null) {
                toolbar.getOverflowIcon().setTint(color);
            }

            for (int i = 0; i < toolbar.getMenu().size(); i++) {
                MenuItem item = toolbar.getMenu().getItem(i);

                if (item.getIcon() != null) {
                    item.getIcon().setTint(color);
                }
            }
        });

        MenuItem themeItem = menu.findItem(R.id.menuTheme);

        int mode = AppCompatDelegate.getDefaultNightMode();

        if (mode == AppCompatDelegate.MODE_NIGHT_YES) {
            themeItem.setTitle("Light Mode");
            themeItem.setIcon(R.drawable.ic_light_mode);
        } else {
            themeItem.setTitle("Dark Mode");
            themeItem.setIcon(R.drawable.ic_dark_mode);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuSearch) {
            startActivity(new Intent(this, SearchActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.menuFavorite) {
            startActivity(new Intent(this, FavoriteActivity.class));
            return true;
        }

        if (item.getItemId() == R.id.menuTheme) {
            ThemeManager.toggleTheme(this);
            recreate();
            return true;
        }

        if (item.getItemId() == R.id.menuAbout) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}