package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setBottomNavigationBar();
    }

    // navigation bar, copy for all function
    public void setBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.miSetting);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId()) {
                    case R.id.miBill:
                        i = new Intent(Setting.this, BillsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miBudget:
                        i = new Intent(Setting.this, MainActivity.class);
                        finish();
                        startActivity(i);
                        break;

                    case R.id.miAnalytic:
                        i = new Intent(Setting.this, AnalyticActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                i = new Intent(Setting.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                // clear shared preference
                preference = getSharedPreferences("User", MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.clear();
                editor.apply();

                startActivity(i);
                break;

            case R.id.btnUpdateProfile:
                i = new Intent(Setting.this, UpdateProfile.class);
                startActivity(i);
                break;
        }
    }
}