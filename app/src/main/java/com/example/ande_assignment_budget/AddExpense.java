package com.example.ande_assignment_budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.Expense;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AddExpense extends AppCompatActivity implements View.OnClickListener{

    EditText title, amount, category, notes;
    SqliteDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Spinner dropdown = findViewById(R.id.spinner);

        String[] items = new String[]{"Food", "Transport", "Personal"};

        db  = new SqliteDbHandler(this);
        title = (EditText) findViewById((R.id.expenseTitle));
        amount = (EditText) findViewById((R.id.expenseAmount));
        notes = (EditText) findViewById((R.id.expenseNotes));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        setBottomNavigationBar();
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
                        i = new Intent(AddExpense.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent i = new Intent(this, ExpenseActivity.class);
                startActivity(i);
                break;
            case R.id.button3:
                Intent l = new Intent(this, ScanReceipt.class);
                startActivity(l);
                break;
            case R.id.button:
                String expTitle = title.getText().toString();
                float expAmount = Float.parseFloat(amount.getText().toString());
                Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
                String cat = mySpinner.getSelectedItem().toString();
                int categoryId;
                if(cat == "Transport") {
                    categoryId = 1;
                }
                else if (cat == "Food") {
                    categoryId = 2;
                }
                else {
                    categoryId = 3;
                }
                String expNotes = notes.getText().toString();
                db.addExpense(new Expense(expTitle, categoryId, expNotes, expAmount));
                Intent x = new Intent(this, ExpenseActivity.class);
                startActivity(x);
                break;
        }
    }
}