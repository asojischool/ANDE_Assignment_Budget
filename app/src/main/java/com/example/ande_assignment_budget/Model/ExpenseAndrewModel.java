package com.example.ande_assignment_budget.Model;

public class ExpenseAndrewModel {
    private double expenseAmount;
    private String categoryName;

    public ExpenseAndrewModel(double expenseAmount, String categoryName) {
        this.expenseAmount = expenseAmount;
        this.categoryName = categoryName;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
