package com.example.ande_assignment_budget.Model;

public class PaymentModel {

    private String payName;
    private String paySpent;
    private int payIcon;

    public PaymentModel(String payName, String paySpent, int payIcon) {
        this.payName = payName;
        this.paySpent = paySpent;
        this.payIcon = payIcon;
        count++;
    }

    private int count;

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPaySpent() {
        return paySpent;
    }

    public void setPaySpent(String paySpent) {
        this.paySpent = paySpent;
    }

    public int getPayIcon() {
        return payIcon;
    }

    public void setPayIcon(int payIcon) {
        this.payIcon = payIcon;
    }

    public int getCount() {
        return count;
    }
}
