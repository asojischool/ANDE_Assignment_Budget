package com.example.ande_assignment_budget.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.Model.CategoryModel;
import com.example.ande_assignment_budget.Model.ExpenseBudgetModel;
import com.example.ande_assignment_budget.R;
import com.example.ande_assignment_budget.SetBudget;
import com.example.ande_assignment_budget.Utility.MathMethods;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class BudgetHistory_RecyclerViewAdapter extends RecyclerView.Adapter<BudgetHistory_RecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<ExpenseBudgetModel> expenseBudgetModels;

    // constructor
    public BudgetHistory_RecyclerViewAdapter(Context context, ArrayList<ExpenseBudgetModel> expenseBudgetModels, String userId) {
        this.context = context;
        this.expenseBudgetModels = expenseBudgetModels;
    }

    @NonNull
    @Override
    public BudgetHistory_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout (Giving a look to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.budget_history_recycler_view_row, parent, false);
        return new BudgetHistory_RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetHistory_RecyclerViewAdapter.ViewHolder holder, int position) {
        // assigning values to the views we created in the category_recycler_view layout file
        // based on the position of the recycler view

        // get the dates from Context
        // call db to get sum of budget and expense used for each date
        // set the value for each

        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();

        holder.tvDate.setText(months[expenseBudgetModels.get(position).getMonth() - 1] + " " + expenseBudgetModels.get(position).getYear());
        holder.tvBudgetAmount.setText(MathMethods.round(expenseBudgetModels.get(position).getTotalBudget(), 2) + "");
        holder.tvSpentAmount.setText(MathMethods.round(expenseBudgetModels.get(position).getTotalExpense(), 2) + "");

    }

    @Override
    public int getItemCount() {
        return expenseBudgetModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from category_recycler_view layout file
        // Kinda like in the onCreate method

        TextView tvDate, tvBudgetAmount, tvSpentAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvBudgetAmount = itemView.findViewById(R.id.tvBudgetAmount);
            tvSpentAmount = itemView.findViewById(R.id.tvSpentAmount);
        }
    }
}
