package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ande_assignment_budget.Adapter.CategorySpent_RecyclerViewAdapter;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.CategoryModel;
import com.example.ande_assignment_budget.Utility.MathMethods;
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
    private SharedPreferences preference;
    private final Calendar today = Calendar.getInstance(TimeZone.getTimeZone("SGT"));
    private int month = today.get(Calendar.MONTH);
    private int year = today.get(Calendar.YEAR);

    TextView tvTotalAmtSpent, tvMonthlyBudget, tvLeftToSpendAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        seedDatabase();
        monthYearPickerDialog();
        setUpCategoryModels();
        setRecyclerList();
        setBottomNavigationBar();
        setUpStatistics();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpCategoryModels();
        setRecyclerList();
    }

    private void setUpCategoryModels() {
        preference = getSharedPreferences("User", MODE_PRIVATE);

        db = new SqliteDbHandler(this);
        categoryModels = db.getCurrentBudgetByMonth(month+1, year, preference.getString("userId", null));
    }

    @SuppressLint("SetTextI18n")
    private void setUpStatistics() {
        tvTotalAmtSpent = findViewById(R.id.tvTotalAmtSpent);
        tvMonthlyBudget = findViewById(R.id.tvMonthlyBudget);
        tvLeftToSpendAmt = findViewById(R.id.tvLeftToSpendAmt);

        db = new SqliteDbHandler(this);
        preference = getSharedPreferences("User", MODE_PRIVATE);

        String userId = preference.getString("userId", null);

        double sumExpenseByMonth = db.getSumExpenseByMonth(month+1, year, userId);
        double sumMonthlyBudget = db.getSumMonthlyBudget(month+1, year, userId);

        tvTotalAmtSpent.setText("" + MathMethods.round(sumExpenseByMonth, 2));
        tvMonthlyBudget.setText("" + MathMethods.round(sumMonthlyBudget, 2));
        tvLeftToSpendAmt.setText("" + MathMethods.round(sumMonthlyBudget - sumExpenseByMonth, 2));
    }

    private void setRecyclerList() {
        RecyclerView rvCategorySpent = findViewById(R.id.rvCategorySpent);

        CategorySpent_RecyclerViewAdapter adapter = new CategorySpent_RecyclerViewAdapter(this, categoryModels);
        rvCategorySpent.setAdapter(adapter);
        rvCategorySpent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void monthYearPickerDialog() {
        changeDate(dateToString(today.get(Calendar.MONTH)), today.get(Calendar.MONTH), today.get(Calendar.YEAR));

        builder = new MonthPickerDialog.Builder(MainActivity.this,
                (selectedMonth, selectedYear) -> changeDate(dateToString(selectedMonth), selectedMonth, selectedYear),
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
            case R.id.btnBudgetHistory:
                i = new Intent(MainActivity.this, BudgetHistory.class);
                startActivity(i);
                break;
        }

    }

    public void changeDate(String selectedMonth, int selectedMonthInteger, int selectedYear) {
        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setText(String.format(Locale.ENGLISH, "%s %d", selectedMonth, selectedYear));

        month = selectedMonthInteger;
        year = selectedYear;
        setUpCategoryModels();
        setUpStatistics();
        setRecyclerList();
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
                        finish();
                        startActivity(i);
                        break;

                    case R.id.miAnalytic:
                        i = new Intent(MainActivity.this, AnalyticActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miBill:
                        i = new Intent(MainActivity.this, BillsActivity.class);
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