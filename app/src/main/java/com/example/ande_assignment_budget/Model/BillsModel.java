package com.example.ande_assignment_budget.Model;

public class BillsModel {
    private String billName;
    private double billAmt;
    private int billDueDay;
    private int billStatus;

    public BillsModel(String billName, double billAmt, int billDueDay, int billStatus) {
        this.billName = billName;
        this.billAmt = billAmt;
        this.billDueDay = billDueDay;
        this.billStatus = billStatus;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public int getBillDueDay() {
        return billDueDay;
    }

    public void setBillDueDay(int billDueDay) {
        this.billDueDay = billDueDay;
    }

    public void setBillAmt(double billAmt) {
        this.billAmt = billAmt;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public String getBillName() {
        return billName;
    }

    public double getBillAmt() {
        return billAmt;
    }

    public int getBillStatus() {
        return billStatus;
    }
}
