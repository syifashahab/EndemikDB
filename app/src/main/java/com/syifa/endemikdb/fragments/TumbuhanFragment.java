package com.syifa.endemikdb.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.syifa.endemikdb.R;
import com.syifa.endemikdb.activities.DetailActivity;
import com.syifa.endemikdb.activities.SearchActivity;
import com.syifa.endemikdb.adapters.EndemikAdapter;
import com.syifa.endemikdb.database.EndemikDatabase;
import com.syifa.endemikdb.entities.Endemik;

import java.util.List;

public class TumbuhanFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChipGroup chipGroupRegion;
    private EndemikDatabase database;

    private MaterialCardView cardSearch;
    private ImageView imgBanner;

    public TumbuhanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(
                R.layout.fragment_tumbuhan,
                container,
                false
        );

        recyclerView = view.findViewById(R.id.rvTumbuhan);

        chipGroupRegion = view.findViewById(R.id.chipGroupRegion);

        cardSearch = view.findViewById(R.id.cardSearch);
        imgBanner = view.findViewById(R.id.imgBanner);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        layoutManager.setInitialPrefetchItemCount(4);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(6);
        recyclerView.setItemAnimator(null);

        database = EndemikDatabase.getInstance(requireContext());

        loadRecycler(database.endemikDao().getTumbuhanHome());

        cardSearch.setOnClickListener(v -> {

            v.animate()
                    .scaleX(0.98f)
                    .scaleY(0.98f)
                    .setDuration(80)
                    .withEndAction(() -> {

                        v.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(120)
                                .start();

                        startActivity(new Intent(
                                getContext(),
                                SearchActivity.class
                        ));

                        requireActivity().overridePendingTransition(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left
                        );

                    })
                    .start();

        });

        imgBanner.animate()
                .scaleX(1.08f)
                .scaleY(1.08f)
                .setDuration(12000)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        chipGroupRegion.setOnCheckedStateChangeListener((group, checkedIds) -> {

            if (checkedIds.isEmpty()) return;

            Chip chip = group.findViewById(checkedIds.get(0));

            String region = chip.getText().toString();

            if (region.equals("Semua")) {
                loadRecycler(database.endemikDao().getTumbuhan());
            } else {
                loadRecycler(database.endemikDao().getTumbuhanByRegion(region));
            }

        });

        return view;
    }

    private void loadRecycler(List<Endemik> list) {

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();

        int itemCount = list.size();
        int rows = (int) Math.ceil(itemCount / 2.0);

        float cardHeightDp = 310f;
        float density = getResources().getDisplayMetrics().density;

        params.height = (int) ((rows * cardHeightDp + 40) * density);

        recyclerView.setLayoutParams(params);

        EndemikAdapter adapter = new EndemikAdapter(list, endemik -> {

            Intent intent = new Intent(getContext(), DetailActivity.class);

            intent.putExtra("id", endemik.getId());
            intent.putExtra("nama", endemik.getNama());
            intent.putExtra("latin", endemik.getNama_latin());
            intent.putExtra("asal", endemik.getAsal());
            intent.putExtra("status", endemik.getStatus());
            intent.putExtra("deskripsi", endemik.getDeskripsi());
            intent.putExtra("foto", endemik.getFoto());
            intent.putExtra("tipe", endemik.getTipe());

            startActivity(intent);

            requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );

        });

        recyclerView.setAdapter(adapter);
    }
}