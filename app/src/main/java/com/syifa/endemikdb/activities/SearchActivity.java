package com.syifa.endemikdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.syifa.endemikdb.R;
import com.syifa.endemikdb.adapters.EndemikAdapter;
import com.syifa.endemikdb.database.EndemikDatabase;
import com.syifa.endemikdb.entities.Endemik;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView recyclerView;
    private View layoutEmpty;

    private EndemikDatabase database;
    private List<Endemik> allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.rvSearch);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        database = EndemikDatabase.getInstance(this);
        allData = database.endemikDao().getAll();

        loadData(allData);

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<Endemik> filtered = new ArrayList<>();

                String keyword = s.toString().trim().toLowerCase();

                for (Endemik item : allData) {

                    String nama = item.getNama() == null ? "" : item.getNama().toLowerCase();
                    String latin = item.getNama_latin() == null ? "" : item.getNama_latin().toLowerCase();
                    String asal = item.getAsal() == null ? "" : item.getAsal().toLowerCase();

                    if (nama.contains(keyword)
                            || latin.contains(keyword)
                            || asal.contains(keyword)) {

                        filtered.add(item);
                    }
                }

                loadData(filtered);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void loadData(List<Endemik> list) {

        if (list.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        EndemikAdapter adapter = new EndemikAdapter(list, endemik -> {

            Intent intent = new Intent(SearchActivity.this, DetailActivity.class);

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
        });

        recyclerView.setAdapter(adapter);
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