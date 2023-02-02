package com.example.ande_assignment_budget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BillsEditActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_edit);

        Intent data = getIntent();
        String billName = data.getStringExtra("billName");
        String billAmount = data.getStringExtra("billAmount");
//        String billStatus = data.getStringExtra("billDueDay");

        EditText editBillName = findViewById(R.id.editBillName);
        EditText editBillAmount = findViewById(R.id.editBillAmount);
        editBillName.setText(billName);
        editBillAmount.setText(billAmount);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setBottomNavigationBar();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAddBillFormAddReminder){
            Log.d("TEXT", "Button Clicked");
        }
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
                        i = new Intent(BillsEditActivity.this, BillsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miBudget:
                        i = new Intent(BillsEditActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miSetting:
                        i = new Intent(BillsEditActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }
}
