package com.example.ande_assignment_budget;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BillsAddActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_add);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setBottomNavigationBar();

        EditText editBillDay = findViewById(R.id.editBillDay);
        editBillDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                int maxValue = 31;  // Set the maximum value here

                if (s.length() > 0) {
                    int value = Integer.parseInt(s.toString());
                    if (value > maxValue) {
                        s.replace(0, s.length(), String.valueOf(maxValue));
                    }
                }
            }
        });
    }


    // method for back button to go back previous page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // navigation bar, copy for all function
    public void setBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.miBill);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId()) {
                    case R.id.miBill:
                        i = new Intent(BillsAddActivity.this, BillsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miBudget:
                        i = new Intent(BillsAddActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miSetting:
                        i = new Intent(BillsAddActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAddBillFormAddReminder){
            Log.d("TEXT", "Button Clicked");
        }
    }
}
