package com.example.ande_assignment_budget.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.Model.CategoryModel;
import com.example.ande_assignment_budget.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CategorySpent_RecyclerViewAdapter extends RecyclerView.Adapter<CategorySpent_RecyclerViewAdapter.myViewHolder>  {

    private Context context;
    private ArrayList<CategoryModel> categoryModels;

    // constructor
    public CategorySpent_RecyclerViewAdapter(Context context, ArrayList<CategoryModel> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout (Giving a look to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.category_recycler_view_row, parent, false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        // assigning values to the views we created in the category_recycler_view layout file
        // based on the position of the recycler view

        int[] iconColor = context.getResources().getIntArray(R.array.iconColor);
        int[] iconBgColor = context.getResources().getIntArray(R.array.iconBgColor);
        String catSpendingText = "$" + categoryModels.get(position).getCatSpent();

        holder.tvCatName.setText(categoryModels.get(position).getCatName());
        holder.tvCatSpending.setText(catSpendingText);
        holder.ivCatLogo.setImageResource(categoryModels.get(position).getCatIcon());
        holder.ivCatLogo.setColorFilter(iconColor[position]);
        holder.clLogoBackground.getBackground().setTint(iconBgColor[position]);
    }

    @Override
    public int getItemCount() {
        // the recycler view wants to know the number of items you want to display
        return categoryModels.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from category_recycler_view_row layout file
        // Kinda like in the onCreate method

        TextView tvCatName, tvCatSpending;
        ImageView ivCatLogo;
        ConstraintLayout clLogoBackground;

        myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatName = itemView.findViewById(R.id.tvCatName);
            tvCatSpending = itemView.findViewById(R.id.etCatBudget);
            ivCatLogo = itemView.findViewById(R.id.ivCatLogo);
            clLogoBackground = itemView.findViewById(R.id.clLogoBackground);
        }
    }
}
