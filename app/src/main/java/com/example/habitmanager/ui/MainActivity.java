package com.example.habitmanager.ui;

import android.os.Bundle;

import com.example.habitmanager.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.habitmanager.data.user.model.User;
import com.example.habitmanager.data.user.repository.UserRepository;
import com.example.habitmanager.databinding.ActivityMainBinding;
import com.example.habitmanager.databinding.HeaderNavBinding;
import com.example.habitmanager.preferencies.UserPrefManager;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.MainFragment, R.id.habitListFragment)
                .setOpenableLayout(binding.drawer).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build();
            switch (item.getItemId()){
                case R.id.homeMenu:
                    Navigation.findNavController(this,  R.id.nav_host_fragment_content_main).navigate(R.id.MainFragment, null, navOptions);
                    return true;
                case R.id.listMenu:
                    Navigation.findNavController(this,  R.id.nav_host_fragment_content_main).navigate(R.id.habitListFragment, null, navOptions);
                    return true;
                case R.id.completedMenu:
                    return true;
            }
            return false;
        });

        new UserPrefManager(this).login(UserRepository.getInstance().getList().get(0).getEmail());

        User userLogged = UserRepository.getInstance().getUser(new UserPrefManager(this).getUserEmail());

        View headerView = binding.navigationView.getHeaderView(0);
        ((TextView)headerView.findViewById(R.id.username)).setText(userLogged.getName());
        ((TextView)headerView.findViewById(R.id.email)).setText(userLogged.getEmail());
        ((ImageView)headerView.findViewById(R.id.user_image)).setImageResource(userLogged.getProfilePicture());

        setUpNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener(item ->{
            switch (item.getItemId()){
            }
            binding.drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}