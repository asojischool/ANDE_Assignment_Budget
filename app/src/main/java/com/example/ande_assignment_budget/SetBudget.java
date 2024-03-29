package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class SetBudget extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<CategoryModel> categoryModels;
    private RecyclerView rvBudgetCategory;
    private SqliteDbHandler db;
    private SetBudget_RecyclerViewAdapter adapter;

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


        categoryModels = db.getCurrentBudgetByMonth();

//        categoryModels = new ArrayList<>();
//        categoryModels.add(new CategoryModel("Food", 300, R.drawable.ic_baseline_fastfood));
//        categoryModels.add(new CategoryModel("Transport", 400, R.drawable.ic_baseline_directions_car));
//        categoryModels.add(new CategoryModel("Grocery", 800, R.drawable.ic_baseline_local_grocery_store));
    }

    private void setRecyclerList() {
        rvBudgetCategory = findViewById(R.id.rvBudgetCategory);

        adapter = new SetBudget_RecyclerViewAdapter(this, categoryModels);
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
        bottomNavigationView.setSelectedItemId(R.id.miBudget);
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