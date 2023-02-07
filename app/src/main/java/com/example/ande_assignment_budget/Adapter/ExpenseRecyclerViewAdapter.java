package com.example.ande_assignment_budget.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.Model.ExpenseModel;
import com.example.ande_assignment_budget.R;

import java.util.ArrayList;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseRecyclerViewAdapter.myViewHolder> {

    private Context context;
    private ArrayList<ExpenseModel> expenseModels;

    public ExpenseRecyclerViewAdapter(Context context, ArrayList<ExpenseModel> expenseModels) {
        this.context = context;
        this.expenseModels = expenseModels;
    }

    @NonNull
    @Override
    public ExpenseRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout (Giving a look to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.expense_recycler_view_row, parent, false);

        return new ExpenseRecyclerViewAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        int[] iconColor = context.getResources().getIntArray(R.array.iconColor);
        int[] iconBgColor = context.getResources().getIntArray(R.array.iconBgColor);
        int p = position;

        holder.tvTitle.setText(expenseModels.get(position).getTitle());
        holder.tvSpent.setText(expenseModels.get(position).getSpent());
        holder.ivExpLogo.setImageResource(expenseModels.get(position).getExpIcon());
        if (position > 2) {
            p = position % 3;
        }
        holder.ivExpLogo.setColorFilter(iconColor[p]);
        holder.clLogoBackground.getBackground().setTint(iconBgColor[p]);
    }

    @Override
    public int getItemCount() {
        return expenseModels.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvSpent;
        ImageView ivExpLogo;
        ConstraintLayout clLogoBackground;

        myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSpent = itemView.findViewById(R.id.tvSpent);
            ivExpLogo = itemView.findViewById(R.id.ivExpLogo);
            clLogoBackground = itemView.findViewById(R.id.clLogoBackground);
        }
    }
}
