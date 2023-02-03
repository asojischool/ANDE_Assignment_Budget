package com.example.ande_assignment_budget.Model;

public class ExpenseBudgetModel {

    private int month;
    private int year;
    private double totalBudget;
    private double totalExpense;

    public ExpenseBudgetModel(int month, int year, double totalBudget, double totalExpense) {
        this.month = month;
        this.year = year;
        this.totalBudget = totalBudget;
        this.totalExpense = totalExpense;
    }

    public ExpenseBudgetModel(int month, int year, double totalBudget) {
        this.month = month;
        this.year = year;
        this.totalBudget = totalBudget;
    }

    public int getMonth() { return month; }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
