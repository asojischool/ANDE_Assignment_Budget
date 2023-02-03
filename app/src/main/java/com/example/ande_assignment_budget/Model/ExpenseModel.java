package com.example.ande_assignment_budget.Model;

public class ExpenseModel {

    private String title;
    private String spent;
    private int expIcon;

    public ExpenseModel(String title, String spent, int expIcon) {
        this.title = title;
        this.spent = spent;
        this.expIcon = expIcon;
        count++;
    }

    private int count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpent() {
        return spent;
    }

    public void setSpent(String spent) {
        this.spent = spent;
    }

    public int getExpIcon() {
        return expIcon;
    }

    public void setExpIcon(int expIcon) {
        this.expIcon = expIcon;
    }

    public int getCount() {
        return count;
    }
}
