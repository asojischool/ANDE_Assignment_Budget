package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.ande_assignment_budget.Adapter.AnalyticActivity_RecyclerViewAdapter;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.ExpenseAndrewModel;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AnalyticActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences preference;
    private PieChart piechart;
    private SqliteDbHandler db;
    private MonthPickerDialog.Builder builder;
    private ArrayList<ExpenseAndrewModel> expenseAndrewModel;
    private final Calendar today = Calendar.getInstance(TimeZone.getTimeZone("SGT"));
    private int month = today.get(Calendar.MONTH);
    private int year = today.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytic);

        // set action bar
        // calling the action bar and show back button
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Monthly Expense Analytic");
        }

        monthYearPickerDialog();
        setData();
    }

    private void setData() {
        // Set the data and color to the pie chart

        preference = getSharedPreferences("User", MODE_PRIVATE);
        String userId = preference.getString("userId", null);

        // get data from db
        db = new SqliteDbHandler(this);
        expenseAndrewModel = db.getSumExpenseEachCatByMonth(month + 1, year, userId);

        piechart = findViewById(R.id.piechart);

        int[] iconColor = this.getResources().getIntArray(R.array.iconColor);

        for (int i = 0; i < expenseAndrewModel.size(); i++) {
            ExpenseAndrewModel currentTotalExpenseCategory = expenseAndrewModel.get(i);
            piechart.addPieSlice(new PieModel(currentTotalExpenseCategory.getCategoryName(), (float) currentTotalExpenseCategory.getExpenseAmount()
                    , iconColor[i]));
        }

        // set legend
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        AnalyticActivity_RecyclerViewAdapter adapter = new AnalyticActivity_RecyclerViewAdapter(expenseAndrewModel, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // textView get

        piechart.startAnimation();
    }

    private void monthYearPickerDialog() {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("SGT"));
        changeDate(dateToString(today.get(Calendar.MONTH)), today.get(Calendar.YEAR));

        builder = new MonthPickerDialog.Builder(AnalyticActivity.this,
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

    public void changeDate(String selectedMonth, int selectedMonthInteger, int selectedYear) {
        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setText(String.format(Locale.ENGLISH, "%s %d", selectedMonth, selectedYear));

        month = selectedMonthInteger;
        year = selectedYear;
        setData();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeDate(String selectedMonth, int selectedYear) {
        Button btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setText(String.format(Locale.ENGLISH, "%s %d", selectedMonth, selectedYear));
    }

    public String dateToString(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}