package com.syifa.endemikdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.syifa.endemikdb.R;
import com.syifa.endemikdb.adapters.FavoriteAdapter;
import com.syifa.endemikdb.database.EndemikDatabase;
import com.syifa.endemikdb.entities.Endemik;
import com.syifa.endemikdb.entities.Favorite;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private View layoutEmpty;
    private EndemikDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.rvFavorite);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        database = EndemikDatabase.getInstance(this);

        loadFavorite();
    }

    private void loadFavorite() {

        List<Favorite> list = database.favoriteDao().getAll();

        if (list.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        FavoriteAdapter adapter = new FavoriteAdapter(

                list,
                
                favorite -> {

                    Endemik endemik = database.endemikDao().getById(favorite.getId());

                    if (endemik == null) return;

                    Intent intent = new Intent(
                            FavoriteActivity.this,
                            DetailActivity.class
                    );

                    intent.putExtra("id", endemik.getId());
                    intent.putExtra("nama", endemik.getNama());
                    intent.putExtra("latin", endemik.getNama_latin());
                    intent.putExtra("asal", endemik.getAsal());
                    intent.putExtra("status", endemik.getStatus());
                    intent.putExtra("deskripsi", endemik.getDeskripsi());
                    intent.putExtra("foto", endemik.getFoto());
                    intent.putExtra("tipe", endemik.getTipe());

                    startActivity(intent);

                    overridePendingTransition(
                            R.anim.slide_in_right,
                            R.anim.slide_out_left
                    );

                },

                favorite -> {

                    new AlertDialog.Builder(FavoriteActivity.this)
                            .setTitle("Remove Favorite")
                            .setMessage("Remove \"" + favorite.getNama() + "\" from favorites?")
                            .setPositiveButton("Remove", (dialog, which) -> {

                                database.favoriteDao().deleteById(favorite.getId());

                                Toast.makeText(
                                        FavoriteActivity.this,
                                        "Removed from favorites",
                                        Toast.LENGTH_SHORT
                                ).show();

                                loadFavorite();

                            })
                            .setNegativeButton("Cancel", null)
                            .show();

                }

        );

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorite();
    }
}