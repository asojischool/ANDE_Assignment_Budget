package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ande_assignment_budget.Adapter.ExpenseRecyclerViewAdapter;
import com.example.ande_assignment_budget.Model.ExpenseModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    private ArrayList<ExpenseModel> expenseModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        setBottomNavigationBar();
        setUpExpenseModels();
        setRecyclerList();
    }

    private void setUpExpenseModels() {
        expenseModels = new ArrayList<>();
        expenseModels.add(new ExpenseModel("Astons", "$300", R.drawable.ic_baseline_fastfood));
        expenseModels.add(new ExpenseModel("Challenger", "$400", R.drawable.ic_baseline_directions_car));
        expenseModels.add(new ExpenseModel("IKEA", "$800", R.drawable.ic_baseline_local_grocery_store));
    }

    private void setRecyclerList() {
        RecyclerView rvExpense = findViewById(R.id.rvExpense);

        ExpenseRecyclerViewAdapter adapter = new ExpenseRecyclerViewAdapter(this, expenseModels);
        rvExpense.setAdapter(adapter);
        rvExpense.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.miBudget);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId()) {
                    case R.id.miSetting:
                        i = new Intent(ExpenseActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }
}