package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ande_assignment_budget.Adapter.CategorySpent_RecyclerViewAdapter;
import com.example.ande_assignment_budget.Model.CategoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<CategoryModel> categoryModels;
    private MonthPickerDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthYearPickerDialog();
        setUpCategoryModels();
        setRecyclerList();
        setBottomNavigationBar();
    }

    private void setUpCategoryModels() {
        categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel("Food", "$300", R.drawable.ic_baseline_fastfood));
        categoryModels.add(new CategoryModel("Transport", "$400", R.drawable.ic_baseline_directions_car));
        categoryModels.add(new CategoryModel("Grocery", "$800", R.drawable.ic_baseline_local_grocery_store));
    }

    private void setRecyclerList() {
        RecyclerView rvCategorySpent = findViewById(R.id.rvCategorySpent);

        CategorySpent_RecyclerViewAdapter adapter = new CategorySpent_RecyclerViewAdapter(this, categoryModels);
        rvCategorySpent.setAdapter(adapter);
        rvCategorySpent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void monthYearPickerDialog() {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("SGT"));
        changeDate(dateToString(today.get(Calendar.MONTH)), today.get(Calendar.YEAR));

        builder = new MonthPickerDialog.Builder(MainActivity.this,
                (selectedMonth, selectedYear) -> changeDate(dateToString(selectedMonth), selectedYear),
                today.get(Calendar.YEAR), today.get(Calendar.MONTH)
        );

        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(today.get(Calendar.YEAR))
                .setActivatedMonth(today.get(Calendar.MONTH))
                .setTitle("Select month");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDatePicker) {
            builder.build().show();
        }
    }

    public void changeDate(String selectedMonth, int selectedYear) {
        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setText(String.format(Locale.ENGLISH, "%s %d", selectedMonth, selectedYear));
    }

    public String dateToString(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    // navigation bar use for all function
    public void setBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.miBudget);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId()) {
                    case R.id.miSetting:
                        i = new Intent(MainActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

}