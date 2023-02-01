package com.example.ande_assignment_budget.Model;

import android.graphics.drawable.Drawable;

public class CategoryModel {

    private String catName;
    private double catSpent;
    private int catIcon;
    private int catId;

    public CategoryModel(String catName, double catSpent, int catIcon) {
        this.catName = catName;
        this.catSpent = catSpent;
        this.catIcon = catIcon;
    }

    public CategoryModel(String catName, double catSpent, int catIcon, int catId) {
        this.catName = catName;
        this.catSpent = catSpent;
        this.catIcon = catIcon;
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public double getCatSpent() {
        return catSpent;
    }

    public void setCatSpent(double catSpent) {
        this.catSpent = catSpent;
    }

    public int getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(int catIcon) {
        this.catIcon = catIcon;
    }

    public int getCatId() {
        return catId;
    }
}
