package com.example.randomnumber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ToolsPagerAdapter extends FragmentStateAdapter {

    public ToolsPagerAdapter(@NonNull AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new RandomNumberFragment();
        }
        if (position == 1) {
            return new BatchPickFragment();
        }
        return new ShuffleFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
