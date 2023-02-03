package com.example.ande_assignment_budget.Adapter;

import static java.lang.Math.round;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande_assignment_budget.DatabaseHandler.SqliteDbHandler;
import com.example.ande_assignment_budget.MainActivity;
import com.example.ande_assignment_budget.Model.CategoryModel;
import com.example.ande_assignment_budget.R;
import com.example.ande_assignment_budget.SetBudget;
import com.example.ande_assignment_budget.Utility.MathMethods;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SetBudget_RecyclerViewAdapter extends RecyclerView.Adapter<SetBudget_RecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<CategoryModel> categoryModels;
    private final String userId;
    private List<EditText> etCatBudgetsValues = new ArrayList<>();

    private final ArrayList<Double> tempBudgetValues = new ArrayList<>();
    private double sum = 0;

    // constructor
    public SetBudget_RecyclerViewAdapter(Context context, ArrayList<CategoryModel> categoryModels, String userId) {
        this.context = context;
        this.categoryModels = categoryModels;
        this.userId = userId;

        for (int i = 0; i < categoryModels.size(); i++) {
            tempBudgetValues.add(categoryModels.get(i).getCatSpent());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout (Giving a look to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.set_budget_recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // assigning values to the views we created in the category_recycler_view layout file
        // based on the position of the recycler view

        int[] iconColor = context.getResources().getIntArray(R.array.iconColor);
        int[] iconBgColor = context.getResources().getIntArray(R.array.iconBgColor);

        holder.tvCatName.setText(categoryModels.get(position).getCatName());
        holder.etCatBudget.setText(String.valueOf(categoryModels.get(position).getCatSpent()));
        holder.ivCatLogo.setImageResource(categoryModels.get(position).getCatIcon());
        holder.ivCatLogo.setColorFilter(iconColor[position]);
        holder.clLogoBackground.getBackground().setTint(iconBgColor[position]);

        etCatBudgetsValues.add(holder.etCatBudget);

        sum += categoryModels.get(position).getCatSpent();

        if (position == getItemCount()-1) {
            ((SetBudget)context).setBudgetSum(sum);
        }

        // text watcher update sum when text change
        holder.etCatBudget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String budgetStr = s.toString();
//                TextView tvTotalBudgetAmount = ((SetBudget)context).findViewById(R.id.tvTotalBudgetAmount);

                if (!budgetStr.isEmpty()) {
                    double budgetValue = Double.parseDouble(budgetStr);
                    tempBudgetValues.set(holder.getBindingAdapterPosition(), budgetValue);
                } else {
                    tempBudgetValues.set(holder.getBindingAdapterPosition(), 0.0);
                }
                double sum = 0.0;
                for (double value : tempBudgetValues) {
                    sum += value;
                }

                ((SetBudget)context).setBudgetSum(MathMethods.round(sum, 2));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // grabbing the views from category_recycler_view layout file
        // Kinda like in the onCreate method

        TextView tvCatName; // category name
        EditText etCatBudget; // edit budget value
        ImageView ivCatLogo; // icon
        ConstraintLayout clLogoBackground; // icon background color

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCatName = itemView.findViewById(R.id.tvCatName);
            etCatBudget = itemView.findViewById(R.id.etCatBudget);
            ivCatLogo = itemView.findViewById(R.id.ivCatLogo);
            clLogoBackground = itemView.findViewById(R.id.clLogoBackground);
        }
    }

    public void updateBudgetAmount() {
        for (int i = 0; i < etCatBudgetsValues.size(); i++) {
            EditText etBudgetValue = etCatBudgetsValues.get(i);
            Double budgetAmount = MathMethods.round(Double.parseDouble(etBudgetValue.getText().toString()), 2);
            int catId = categoryModels.get(i).getCatId();

            // add to database
            Log.d("User Id", ""+userId);
            SqliteDbHandler db = new SqliteDbHandler(context);
            db.setCurrentBudgetByMonth(catId, budgetAmount, userId);
        }
    }
}
