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

import com.example.ande_assignment_budget.Model.PaymentModel;
import com.example.ande_assignment_budget.R;

import java.util.ArrayList;

 public class PaymentRecyclerViewAdapter extends RecyclerView.Adapter<PaymentRecyclerViewAdapter.myViewHolder> {
    private Context context;
    private ArrayList<PaymentModel> paymentModels;

    public PaymentRecyclerViewAdapter(Context context, ArrayList<PaymentModel> paymentModels) {
        this.context = context;
        this.paymentModels = paymentModels;
    }

    @NonNull
    @Override
    public PaymentRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This is where you inflate the layout (Giving a look to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.payment_recycler_view_row, parent, false);

        return new PaymentRecyclerViewAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRecyclerViewAdapter.myViewHolder holder, int position) {

        int[] iconColor = context.getResources().getIntArray(R.array.iconColor);
        int[] iconBgColor = context.getResources().getIntArray(R.array.iconBgColor);

        holder.tvPayName.setText(paymentModels.get(position).getPayName());
        holder.tvPaySpending.setText(paymentModels.get(position).getPaySpent());
        holder.ivPayLogo.setImageResource(paymentModels.get(position).getPayIcon());
        holder.ivPayLogo.setColorFilter(iconColor[position]);
        holder.clLogoBackground.getBackground().setTint(iconBgColor[position]);
    }

    @Override
    public int getItemCount() {
        return paymentModels.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvPayName, tvPaySpending;
        ImageView ivPayLogo;
        ConstraintLayout clLogoBackground;

        myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPayName = itemView.findViewById(R.id.tvPayName);
            tvPaySpending = itemView.findViewById(R.id.tvPaySpending);
            ivPayLogo = itemView.findViewById(R.id.ivPayLogo);
            clLogoBackground = itemView.findViewById(R.id.clLogoBackground);
        }
    }
}
