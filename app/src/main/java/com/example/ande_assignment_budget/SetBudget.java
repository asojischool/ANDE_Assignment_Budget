package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ande_assignment_budget.Adapter.CategorySpent_RecyclerViewAdapter;
import com.example.ande_assignment_budget.Adapter.SetBudget_RecyclerViewAdapter;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.CategoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class SetBudget extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<CategoryModel> categoryModels;
    private RecyclerView rvBudgetCategory;
    private SqliteDbHandler db;
    private SetBudget_RecyclerViewAdapter adapter;
    private SharedPreferences preference;
    private final Calendar today = Calendar.getInstance(TimeZone.getTimeZone("SGT"));
    private int month = today.get(Calendar.MONTH);
    private int year = today.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_budget);

        // set action bar
        // calling the action bar and show back button
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Set Monthly Budget");
        }

        setUpCategoryModels();
        setRecyclerList();
        setBottomNavigationBar();
    }

    private void setUpCategoryModels() {
        db = new SqliteDbHandler(this);

        preference = getSharedPreferences("User", MODE_PRIVATE);
        categoryModels = db.getCurrentBudgetByMonth(month + 1, year, preference.getString("userId", null));
    }

    private void setRecyclerList() {
        rvBudgetCategory = findViewById(R.id.rvBudgetCategory);

        preference = getSharedPreferences("User", MODE_PRIVATE);

        adapter = new SetBudget_RecyclerViewAdapter(this, categoryModels, preference.getString("userId", null));
        rvBudgetCategory.setAdapter(adapter);
        rvBudgetCategory.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setBudgetSum(Double sum) {
        ((TextView) findViewById(R.id.tvTotalBudgetAmount)).setText("" + sum);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSetBudget:
                adapter.updateBudgetAmount();
                Toast.makeText(this, "Budget Updated", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // navigation bar use for all function
    public void setBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.miBudget:
                        i = new Intent(SetBudget.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miSetting:
                        i = new Intent(SetBudget.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }
}