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

import com.example.ande_assignment_budget.Adapter.PaymentRecyclerViewAdapter;
import com.example.ande_assignment_budget.Model.PaymentModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<PaymentModel> paymentModels;
    private MonthPickerDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        monthYearPickerDialog();
        setBottomNavigationBar();
        setUpPaymentModels();
        setRecyclerList();
    }

    private void setUpPaymentModels() {
        paymentModels = new ArrayList<>();
        paymentModels.add(new PaymentModel("GrabPay", "$300", R.drawable.ic_baseline_fastfood));
        paymentModels.add(new PaymentModel("Visa", "$400", R.drawable.ic_baseline_directions_car));
        paymentModels.add(new PaymentModel("GooglePay", "$800", R.drawable.ic_baseline_local_grocery_store));
    }

    private void setRecyclerList() {
        RecyclerView rvPayment = findViewById(R.id.rvPayment);

        PaymentRecyclerViewAdapter adapter = new PaymentRecyclerViewAdapter(this, paymentModels);
        rvPayment.setAdapter(adapter);
        rvPayment.setLayoutManager(new LinearLayoutManager(this));
    }

    private void monthYearPickerDialog() {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("SGT"));
        changeDate(dateToString(today.get(Calendar.MONTH)), today.get(Calendar.YEAR));

        builder = new MonthPickerDialog.Builder(PaymentActivity.this,
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
        switch (v.getId()) {
            case R.id.btnDatePicker:
                builder.build().show();
                break;
            case R.id.button9:
                Intent i = new Intent(this, ExpenseActivity.class);
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
                switch(item.getItemId()) {
                    case R.id.miSetting:
                        i = new Intent(PaymentActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }
}
