package com.syifa.endemikdb.activities;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.appbar.MaterialToolbar;
import com.syifa.endemikdb.R;
import com.syifa.endemikdb.database.EndemikDatabase;
import com.syifa.endemikdb.entities.Favorite;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getIntent().getStringExtra("nama"));

        toolbar.setNavigationOnClickListener(v -> finish());

        ImageView img = findViewById(R.id.imgDetail);

        TextView nama = findViewById(R.id.tvNama);
        TextView latin = findViewById(R.id.tvLatin);
        TextView deskripsi = findViewById(R.id.tvDeskripsi);

        Chip asal = findViewById(R.id.chipAsal);
        Chip status = findViewById(R.id.chipStatus);

        ImageButton btnFavorite = findViewById(R.id.btnFavorite);

        View layoutChip = findViewById(R.id.layoutChip);
        View cardDescription = findViewById(R.id.cardDescription);

        EndemikDatabase db = EndemikDatabase.getInstance(this);

        String id = getIntent().getStringExtra("id");

        nama.setText(getIntent().getStringExtra("nama"));
        latin.setText(getIntent().getStringExtra("latin"));

        asal.setText("📍 " + getIntent().getStringExtra("asal"));

        String statusText = getIntent().getStringExtra("status");
        status.setText(statusText);

        if (statusText != null) {

            if (statusText.equalsIgnoreCase("Aman")) {
                status.setChipBackgroundColorResource(R.color.statusSafe);
            } else {
                status.setChipBackgroundColorResource(R.color.statusDanger);
            }

        }

        deskripsi.setText(getIntent().getStringExtra("deskripsi"));

        Glide.with(this)
                .load(getIntent().getStringExtra("foto"))
                .into(img);

        if (db.favoriteDao().isFavorite(id)) {
            btnFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            btnFavorite.setImageResource(R.drawable.ic_favorite_border);
        }

        btnFavorite.setOnClickListener(v -> {

            v.animate()
                    .scaleX(0.75f)
                    .scaleY(0.75f)
                    .setDuration(80)
                    .withEndAction(() -> {

                        if (db.favoriteDao().isFavorite(id)) {

                            db.favoriteDao().deleteById(id);

                            btnFavorite.setImageResource(R.drawable.ic_favorite_border);
                            btnFavorite.setColorFilter(getColor(R.color.primary));

                            Toast.makeText(
                                    DetailActivity.this,
                                    "Removed from favorites",
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            Favorite favorite = new Favorite();

                            favorite.setId(id);
                            favorite.setNama(getIntent().getStringExtra("nama"));
                            favorite.setTipe(getIntent().getStringExtra("tipe"));
                            favorite.setFoto(getIntent().getStringExtra("foto"));

                            db.favoriteDao().insert(favorite);

                            btnFavorite.setImageResource(R.drawable.ic_favorite);
                            btnFavorite.setColorFilter(getColor(R.color.primary));

                            Toast.makeText(
                                    DetailActivity.this,
                                    "Added to favorites",
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                        btnFavorite.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(150)
                                .start();

                    })
                    .start();
        });

        nama.setAlpha(0f);
        layoutChip.setAlpha(0f);
        cardDescription.setAlpha(0f);

        nama.setTranslationY(40f);
        layoutChip.setTranslationY(40f);
        cardDescription.setTranslationY(40f);

        nama.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(300)
                .start();

        layoutChip.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(120)
                .setDuration(300)
                .start();

        cardDescription.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(240)
                .setDuration(300)
                .start();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
        );
    }
}