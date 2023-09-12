package com.example.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.submission.fragments.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
Fragment fragment;
Toolbar toolbar;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        if (savedInstanceState != null) {
            // Restore state from the saved Bundle
            String value = savedInstanceState.getString("key");
            // Restore other state data as needed
        }

        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.lines);


        fragment = new HomeFragment();
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_container, fragment);
        fragmentTransaction.commit();
    }

     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.main_menu, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem menu) {
        int id = menu.getItemId();

        if(id == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(HomePage.this, sign_up.class));
            finish();
        }
        else if(id == R.id.menu_my_cart) {
            startActivity(new Intent(HomePage.this, CartActivity.class));
        }
        return true;
     }
}