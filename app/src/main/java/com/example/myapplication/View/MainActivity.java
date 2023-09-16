package com.example.myapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameMain;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    FloatingActionButton fab;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mainfrag);
            AddControl();

            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            SharedPreferences preferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
            boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                // User is already logged in, navigate to the main screen
                loadFragment(new FragmentHome());
            } else {
                // User is not logged in, navigate to the login screen
                startActivity(new Intent(this, Login_Account.class));
            }
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow); // Use default or custom icon
            String user = getIntent().getStringExtra("user");
            // Set up the BottomNavigationView listener for fragment transactions


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new FragmentGiaoDich());
                }
            });

            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.chartItem) {
                        loadFragment(new FragmentChart(user));
                        return true;
                    } else if (id == R.id.walletItem) {
                        loadFragment(new ThongKe(user));
                        return true;
                    } else if (id == R.id.homeItem) {
                        loadFragment(new FragmentHome(user));
                        return true;
                    }else if (id == R.id.settingItem) {
                        loadFragment(new Account_Info(user));
                        return true;
                    }
                    else {
                        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
            if (savedInstanceState == null) {
                loadFragment(new FragmentHome(user));
            }
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                onBackPressed(); // Handle the back arrow click to go back to the previous fragment
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        protected void loadFragment(Fragment fragment) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameMain, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        private void AddControl() {
            frameMain = findViewById(R.id.frameMain);
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            fab = (FloatingActionButton) findViewById(R.id.fab);

        }


}