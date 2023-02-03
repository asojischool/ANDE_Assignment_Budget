package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ande_assignment_budget.Adapter.BudgetHistory_RecyclerViewAdapter;
import com.example.ande_assignment_budget.Adapter.CategorySpent_RecyclerViewAdapter;
import com.example.ande_assignment_budget.Adapter.SetBudget_RecyclerViewAdapter;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.CategoryModel;
import com.example.ande_assignment_budget.Model.ExpenseBudgetModel;

import java.util.ArrayList;

public class BudgetHistory extends AppCompatActivity {
    private ArrayList<ExpenseBudgetModel> expenseBudgetModel;
    private SqliteDbHandler db;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_history);

        // set action bar
        // calling the action bar and show back button
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Budget History");
        }

        setRecyclerList();
    }

    private void setRecyclerList() {
        // get all dates for budgets
        // where month and year is not current
        // currently limit to 4
        // store the dates
        // send to recyclerview
        preference = getSharedPreferences("User", MODE_PRIVATE);
        db = new SqliteDbHandler(this);

        String userId = preference.getString("userId", null);
        expenseBudgetModel = db.getTotalBudgetExpenseWithinRange(userId);

        RecyclerView rvBudgetHistory = findViewById(R.id.rvBudgetHistory);

        BudgetHistory_RecyclerViewAdapter adapter = new BudgetHistory_RecyclerViewAdapter(this, expenseBudgetModel, userId);
        rvBudgetHistory.setAdapter(adapter);
        rvBudgetHistory.setLayoutManager(new LinearLayoutManager(this));
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
}