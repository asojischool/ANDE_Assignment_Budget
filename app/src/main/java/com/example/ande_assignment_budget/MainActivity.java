package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ande_assignment_budget.Adapter.CategorySpent_RecyclerViewAdapter;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.CategoryModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<CategoryModel> categoryModels;
    private MonthPickerDialog.Builder builder;
    private SqliteDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        monthYearPickerDialog();
        setUpCategoryModels();
        setRecyclerList();
        setBottomNavigationBar();
//        seedDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpCategoryModels();
        setRecyclerList();
    }

    private void setUpCategoryModels() {
        db = new SqliteDbHandler(this);
        categoryModels = db.getCurrentBudgetByMonth();
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
        Intent i;
        switch (v.getId()) {
            case R.id.btnDatePicker:
                builder.build().show();
                break;
            case R.id.btnSetUpBudget:
                i = new Intent(MainActivity.this, SetBudget.class);
                startActivity(i);
                break;
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
                switch (item.getItemId()) {
                    case R.id.miSetting:
                        i = new Intent(MainActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    public void seedDatabase() {
        db = new SqliteDbHandler(this);
        db.seedTable();
    }
}