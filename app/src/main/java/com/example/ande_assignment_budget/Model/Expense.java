package com.example.ande_assignment_budget.Model;

public class Expense {
    String title;
    Integer category;
    String notes;
    Float amount;

    // Constructor
    public Expense(String title, Integer category, String notes, Float amount){
        this.title = title;
        this.category = category;
        this.notes = notes;
        this.amount = amount;
    }

    public Expense() {

    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getCategory(){
        return this.category;
    }

    public void setCategory(int category) {this.category = category;}

    public String getNotes(){
        return this.notes;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public Float getAmount() {return this.amount;}

    public void setAmount(Float amount) {this.amount = amount;}
}
