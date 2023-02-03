package com.example.ande_assignment_budget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.Adapter.Bills_RecyclerViewAdapter;
import com.example.ande_assignment_budget.Adapter.CategorySpent_RecyclerViewAdapter;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.BillsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class BillsActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<BillsModel> billModels;
    private SqliteDbHandler db;
    double totalBill, billPaid, billLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        setBottomNavigationBar();
        setUpBillModels();
        setRecyclerList();

        for(int i = 0; i < billModels.size(); i++){
            totalBill += billModels.get(i).getBillAmt();
            if(billModels.get(i).getBillStatus() == 1){
                billLeft += billModels.get(i).getBillAmt();
            } else {
                billPaid += billModels.get(i).getBillAmt();
            }
        }
        TextView totalBillAmt = findViewById(R.id.tvTotalBills);
        TextView leftBillAmt = findViewById(R.id.tvBillsLeft);
        TextView paidBillAmt = findViewById(R.id.tvBillsPaid);
        totalBillAmt.setText("$" + String.format("%.2f",totalBill));
        leftBillAmt.setText("$" + String.format("%.2f", billLeft));
        paidBillAmt.setText("$" + String.format("%.2f", billPaid));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpBillModels();
        totalBill = 0;
        billLeft = 0;
        billPaid = 0;
        for(int i = 0; i < billModels.size(); i++){
            totalBill += billModels.get(i).getBillAmt();
            if(billModels.get(i).getBillStatus() == 0){
                billLeft += billModels.get(i).getBillAmt();
            } else {
                billPaid += billModels.get(i).getBillAmt();
            }
        }
        TextView totalBillAmt = findViewById(R.id.tvTotalBills);
        TextView leftBillAmt = findViewById(R.id.tvBillsLeft);
        TextView paidBillAmt = findViewById(R.id.tvBillsPaid);
        totalBillAmt.setText("$" + String.format("%.2f",totalBill));
        leftBillAmt.setText("$" + String.format("%.2f", billLeft));
        paidBillAmt.setText("$" + String.format("%.2f", billPaid));
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
                    case R.id.miBudget:
                        i = new Intent(BillsActivity.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.miSetting:
                        i = new Intent(BillsActivity.this, Setting.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    private void setUpBillModels(){
        db =  new SqliteDbHandler(this);
        billModels = db.getAllBills();
    }

    private void setRecyclerList(){
        RecyclerView rvBills = findViewById(R.id.rvBills);

        Bills_RecyclerViewAdapter adapter = new Bills_RecyclerViewAdapter(this, billModels);
        rvBills.setAdapter(adapter);
        rvBills.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch(v.getId()){
            case R.id.btnAddReminder:
                i = new Intent(this, BillsAddActivity.class);
                startActivity(i);
                break;
        }
    }
}
