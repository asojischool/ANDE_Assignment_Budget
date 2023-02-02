package com.example.ande_assignment_budget;

import static android.app.PendingIntent.FLAG_MUTABLE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ande_assignment_budget.BroadcastReceiver.BillReminder;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class BillsAddActivity extends AppCompatActivity implements View.OnClickListener {
    private SqliteDbHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_add);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        setBottomNavigationBar();

        EditText editBillDay = findViewById(R.id.editBillDay);
        editBillDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                int maxValue = 31;  // Set the maximum value here

                if (s.length() > 0) {
                    int value = Integer.parseInt(s.toString());
                    if (value > maxValue) {
                        s.replace(0, s.length(), String.valueOf(maxValue));
                    }
                }
            }
        });
    }

    // method for back button to go back previous page
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // navigation bar, copy for all function
    public void setBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.miBill);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch(item.getItemId()) {
                    case R.id.miBill:
                        i = new Intent(BillsAddActivity.this, BillsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miBudget:
                        i = new Intent(BillsAddActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miSetting:
                        i = new Intent(BillsAddActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAddBillFormAddReminder){
                EditText billName = (EditText) findViewById(R.id.editBillName);
                EditText billAmt = findViewById(R.id.editBillAmount);
                EditText billDueDay = findViewById(R.id.editBillDay);

                String billNameValue = billName.getText().toString();
                double billAmtvalue = Double.parseDouble(billAmt.getText().toString());
                int billDueDayValue = Integer.parseInt(billDueDay.getText().toString());

                if (TextUtils.isEmpty(billNameValue)) {
                    Toast.makeText(getApplicationContext(), "Bill Name Cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(String.valueOf(billAmtvalue))) {
                    Toast.makeText(getApplicationContext(), "Bill Amount cannot be empty", Toast.LENGTH_SHORT).show();
                    return;

                } else if (TextUtils.isEmpty(String.valueOf(billDueDayValue))) {
                    Toast.makeText(getApplicationContext(), "Bill Day cannot be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Random rnd = new Random();
                int uniqueID = 10000000 + rnd.nextInt(90000000);
                db = new SqliteDbHandler(this);
                if (db.createBill(billNameValue, billAmtvalue, billDueDayValue, uniqueID) == true) {
                    Calendar reminderDate = Calendar.getInstance();
                    reminderDate.set(Calendar.DATE, billDueDayValue);
                    setReminder(uniqueID, reminderDate);
                    Toast.makeText(getApplicationContext(), "Bill Reminder Created!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }

    }

    public void setReminder(int reminderId, Calendar date) {
        Intent intent = new Intent(this, BillReminder.class);
        intent.putExtra("REMINDER_ID", reminderId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, reminderId, intent, FLAG_MUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Calculate the interval for repeating the notification on the specific day of each month
        Calendar current = Calendar.getInstance();
        long interval = AlarmManager.INTERVAL_DAY * 30;
        if (date.get(Calendar.MONTH) <= current.get(Calendar.MONTH)) {
            interval += AlarmManager.INTERVAL_DAY * 30;
        }

        // Schedule the repeating reminder
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.getTimeInMillis(), interval, pendingIntent);
        Log.d("INFO", "Reminder with uid " + reminderId + " has been created.");    }
}
