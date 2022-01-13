package com.randika.kamimomen.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LokasiFragmentAdapter extends FragmentStateAdapter {
    public LokasiFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return  new FotografiFragment();
            case 2:
                return  new SouvenirFragment();
        }

        return new WoFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}