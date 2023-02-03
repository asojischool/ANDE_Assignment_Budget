package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ande_assignment_budget.Adapter.ExpenseRecyclerViewAdapter;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.Expense;
import com.example.ande_assignment_budget.Model.ExpenseModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {

    private ArrayList<ExpenseModel> expenseModels;
    SqliteDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        setBottomNavigationBar();
        setUpExpenseModels();
        setRecyclerList();
    }

    private void setUpExpenseModels() {
        db = new SqliteDbHandler(this);
        List<Expense> expenses = db.getAllExpense();
        expenseModels = new ArrayList<>();
        for(Expense ex : expenses) {
            Integer catId = ex.getCategory();
            String amount = ex.getAmount().toString();
            if (catId.equals(1)) {
                expenseModels.add(new ExpenseModel(ex.getTitle(), amount, R.drawable.ic_baseline_directions_car));
            }
            if (catId.equals(2)) {
                expenseModels.add(new ExpenseModel(ex.getTitle(), amount, R.drawable.ic_baseline_fastfood));
            }
            if (catId.equals(3)) {
                expenseModels.add(new ExpenseModel(ex.getTitle(), amount, R.drawable.ic_baseline_local_grocery_store));
            }
        }
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button7:
                Intent i = new Intent(this, ExpenseActivity.class);
                startActivity(i);
                break;
            case R.id.button8:
                Intent l = new Intent(this, PaymentActivity.class);
                startActivity(l);
                break;
        }
    }
}