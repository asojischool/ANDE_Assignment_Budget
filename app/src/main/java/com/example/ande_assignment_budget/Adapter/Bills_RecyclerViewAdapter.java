package com.example.ande_assignment_budget.Adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.BillsEditActivity;
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
        holder.tvBillAmount.setText(billsModels.get(position).getBillAmt());
        holder.tvBillDueDay.setText("Due day: " + billsModels.get(position).getBillDueDay());
    }


    @Override
    public int getItemCount() {
        return billsModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvBillName, tvBillAmount, tvBillDueDay;
        Button btnEditBill;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBillName = itemView.findViewById(R.id.tvBillName);
            tvBillAmount = itemView.findViewById(R.id.tvBillAmount);
            tvBillDueDay = itemView.findViewById(R.id.tvBillDueDay);
            btnEditBill = itemView.findViewById(R.id.btnEditBill);
            btnEditBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), BillsEditActivity.class);
                    i.putExtra("billName", tvBillName.getText().toString());
                    i.putExtra("billAmount", tvBillAmount.getText().toString());
                    i.putExtra("billDueDay", tvBillDueDay.getText().toString());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
