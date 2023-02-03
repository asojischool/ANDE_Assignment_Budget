package com.example.ande_assignment_budget.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.Model.ExpenseAndrewModel;
import com.example.ande_assignment_budget.R;

import java.util.ArrayList;

public class AnalyticActivity_RecyclerViewAdapter extends RecyclerView.Adapter<AnalyticActivity_RecyclerViewAdapter.MyViewHolder>{

    private final ArrayList<ExpenseAndrewModel> data;
    private final Context context;

    public AnalyticActivity_RecyclerViewAdapter(ArrayList<ExpenseAndrewModel> data, Context context) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.analytic_legend_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int[] iconColor = context.getResources().getIntArray(R.array.iconColor);
        double sum = 0;
        String text = "";

        for (ExpenseAndrewModel expense : data) {
            sum += expense.getExpenseAmount();
        }

        holder.textView.setText(data.get(position).getCategoryName() + " " + (int) Math.floor(data.get(position).getExpenseAmount() / sum * 100) + "%");
        holder.iconView.setBackgroundColor(iconColor[position]);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        View iconView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            iconView = itemView.findViewById(R.id.icon_view);
        }
    }
}
