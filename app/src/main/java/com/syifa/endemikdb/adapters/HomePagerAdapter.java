package com.syifa.endemikdb.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.syifa.endemikdb.fragments.HewanFragment;
import com.syifa.endemikdb.fragments.TumbuhanFragment;

public class HomePagerAdapter extends FragmentStateAdapter {

    public HomePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(position == 0){
            return new HewanFragment();
        }

        return new TumbuhanFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
