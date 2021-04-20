package com.geekbrains.notes.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrains.notes.R;

public class Navigation {
    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(Fragment fragment, boolean useBackStack) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (useBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
