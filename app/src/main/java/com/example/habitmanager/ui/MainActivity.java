package com.example.habitmanager.ui;

import static android.os.ext.SdkExtensions.getExtensionVersion;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.habitmanager.R;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
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
import com.example.habitmanager.preferencies.ThemePreferencies;
import com.example.habitmanager.preferencies.UserPrefManager;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private User userLogged;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private UserPrefManager userPrefManager;

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

        placeHolderUserPref();

        pickMedia = registerForActivityResult(new PickVisualMedia(), uri -> {
                    if(uri != null){
                        View headerView = binding.navigationView.getHeaderView(0);
                        Bitmap image = null;
                        try {
                            image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ((ImageView)headerView.findViewById(R.id.user_image)).setImageBitmap(image);
                    }
                });

        setUpNavigationView();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            return;
        }
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {

                } else {

                }
            });

    private void placeHolderUserPref(){
        userPrefManager = new UserPrefManager(this);

        if(!userPrefManager.isUserLogged()) {
            userPrefManager.login(UserRepository.getInstance().getList().get(0).getEmail());
        }

        Log.d("lksdjaf", userPrefManager.getUserEmail());

        userLogged = UserRepository.getInstance().getList().get(0);
        userLogged.setEmail(userPrefManager.getUserEmail());
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

        View headerView = binding.navigationView.getHeaderView(0);
        ((TextView)headerView.findViewById(R.id.username)).setText(userLogged.getName());
        ((TextView)headerView.findViewById(R.id.email)).setText(userLogged.getEmail());
        ((ImageView)headerView.findViewById(R.id.user_image)).setImageResource(userLogged.getProfilePicture());

        headerView.findViewById(R.id.user_image).setOnClickListener(view -> pickMedia());

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            item.setCheckable(true);
            switch (item.getItemId()){
                case R.id.action_mainFragment:
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.MainFragment);
                    binding.bottomNavigation.setSelectedItemId(R.id.homeMenu);
                    break;
                case R.id.action_habitList:
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.habitListFragment);
                    binding.bottomNavigation.setSelectedItemId(R.id.listMenu);
                    break;
                case R.id.action_completed:
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.MainFragment);
                    binding.bottomNavigation.setSelectedItemId(R.id.completedMenu);
                    break;
                case R.id.action_profile:
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.MainFragment);
                    break;
                case R.id.action_settings:
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.settingsFragment);
                    break;
                case R.id.action_showAboutUs:
                    Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.aboutUsFragment);
                    break;
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

    @Override
    public void onBackPressed() {
        if(binding.drawer.isDrawerOpen(GravityCompat.START)){
            binding.drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void pickMedia(){
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(PickVisualMedia.ImageOnly.INSTANCE).build());

    }
}