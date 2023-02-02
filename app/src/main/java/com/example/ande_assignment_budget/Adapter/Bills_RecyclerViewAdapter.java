package com.example.ande_assignment_budget.Adapter;

import static android.app.PendingIntent.FLAG_MUTABLE;
import static android.content.Context.ALARM_SERVICE;
import static androidx.core.app.ActivityCompat.recreate;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.BillsActivity;
import com.example.ande_assignment_budget.BillsEditActivity;
import com.example.ande_assignment_budget.BroadcastReceiver.BillReminder;
import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.Model.BillsModel;
import com.example.ande_assignment_budget.R;

import java.util.ArrayList;

public class Bills_RecyclerViewAdapter extends RecyclerView.Adapter<Bills_RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<BillsModel> billsModels;

    public Bills_RecyclerViewAdapter(Context context, ArrayList<BillsModel> billsModels) {
        this.context = context;
        this.billsModels = billsModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bills_recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvBillName.setText(billsModels.get(position).getBillName());
        holder.tvBillAmount.setText("$" + String.format("%.2f", billsModels.get(position).getBillAmt()));
        holder.tvBillDueDay.setText("Due day: " + billsModels.get(position).getBillDueDay());
        if(billsModels.get(position).getBillStatus() == 0){
            holder.btnPayBill.setEnabled(true);
        } else {
            holder.btnPayBill.setEnabled(false);
            holder.btnPayBill.setText("Paid");
        }
    }


    @Override
    public int getItemCount() {
        return billsModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private SqliteDbHandler db;

        TextView tvBillName, tvBillAmount, tvBillDueDay;
        Button btnPayBill, btnDeleteBill;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBillName = itemView.findViewById(R.id.tvBillName);
            tvBillAmount = itemView.findViewById(R.id.tvBillAmount);
            tvBillDueDay = itemView.findViewById(R.id.tvBillDueDay);
            btnPayBill = itemView.findViewById(R.id.btnPayBill);
            btnDeleteBill = itemView.findViewById(R.id.btnDeleteBill);
            btnPayBill.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(v.getId() == R.id.btnPayBill){
                        db = new SqliteDbHandler(itemView.getContext());
                        db.updateBillStatus(tvBillName.getText().toString());
                        btnPayBill.setText("paid");
                        btnPayBill.setEnabled(false);
                        ((Activity)context).recreate();
                    }
                }
            });

            btnDeleteBill.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(v.getId() == R.id.btnDeleteBill){
                        db = new SqliteDbHandler(itemView.getContext());
                        db.deleteBill(tvBillName.getText().toString());
                        int uid = db.getBillUUIDByName(tvBillName.getText().toString());
                        Intent intent  = new Intent(context, BillReminder.class);
                        intent.putExtra("REMINDER_ID", uid);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,uid, intent, FLAG_MUTABLE);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

                        alarmManager.cancel(pendingIntent);
                        Log.d("INFO", "Reminder with uid" + uid + " deleted.");
                        ((Activity)context).recreate();
                    }
                }
            });
        }
    }
}
