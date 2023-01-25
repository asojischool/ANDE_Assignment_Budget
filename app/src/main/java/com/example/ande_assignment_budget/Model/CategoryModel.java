package com.example.ande_assignment_budget.Model;

import android.graphics.drawable.Drawable;

public class CategoryModel {

    private String catName;
    private String catSpent;
    private int catIcon;

    public CategoryModel(String catName, String catSpent, int catIcon) {
        this.catName = catName;
        this.catSpent = catSpent;
        this.catIcon = catIcon;
        count++;
    }

    private int count;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatSpent() {
        return catSpent;
    }

    public void setCatSpent(String catSpent) {
        this.catSpent = catSpent;
    }

    public int getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(int catIcon) {
        this.catIcon = catIcon;
    }

    public int getCount() {
        return count;
    }
}
