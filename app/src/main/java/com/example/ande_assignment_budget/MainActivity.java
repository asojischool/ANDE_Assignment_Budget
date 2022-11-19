package com.example.ande_assignment_budget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ande_assignment_budget.Model.CategoryModel;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<CategoryModel> categoryModels;
    private MonthPickerDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthYearPickerDialog();
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

    private void monthYearPickerDialog() {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        changeDate(dateToString(today.get(Calendar.MONTH)), today.get(Calendar.YEAR));

        builder = new MonthPickerDialog.Builder(MainActivity.this,
            new MonthPickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(int selectedMonth, int selectedYear) {
                    changeDate(dateToString(selectedMonth), selectedYear);
                }
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH)
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
        Intent i;
        switch (v.getId()) {
            case R.id.btnDatePicker:
                builder.build().show();
                break;
        }
    }

    public void changeDate(String selectedMonth, int selectedYear) {
        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setText(selectedMonth + " " + selectedYear);
    }

    public String dateToString(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}