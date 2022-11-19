package com.example.ande_assignment_budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ande_assignment_budget.Model.CategoryModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CategoryModel> categoryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpCategoryModels();
        setRecyclerList();
    }

    private void setUpCategoryModels() {
        categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel("Food",  "$300", R.drawable.ic_baseline_fastfood));
        categoryModels.add(new CategoryModel("Transport","$400", R.drawable.ic_baseline_directions_car));
        categoryModels.add(new CategoryModel("Grocery", "$800", R.drawable.ic_baseline_local_grocery_store));
    }

    private void setRecyclerList() {
        RecyclerView rvCategorySpent = findViewById(R.id.rvCategorySpent);

        CategorySpent_RecyclerViewAdapter adapter = new CategorySpent_RecyclerViewAdapter(this, categoryModels);
        rvCategorySpent.setAdapter(adapter);
        rvCategorySpent.setLayoutManager(new LinearLayoutManager(this));
    }
}