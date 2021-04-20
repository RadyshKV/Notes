package com.geekbrains.notes;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.geekbrains.notes.observe.Publisher;
import com.geekbrains.notes.ui.AboutFragment;
import com.geekbrains.notes.ui.Navigation;
import com.geekbrains.notes.ui.NotesFragment;
import com.geekbrains.notes.ui.SettingsFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private Navigation navigation;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = new Navigation(getSupportFragmentManager());
        initView();
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        getNavigation().addFragment(new NotesFragment(), false);
    }


    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        return toolbar;
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                getNavigation().addFragment(new SettingsFragment(), true);
                return true;
            case R.id.action_main:
                getNavigation().addFragment(new NotesFragment(), true);
                return true;
            case R.id.action_about:
                getNavigation().addFragment(new AboutFragment(), true);
                return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

}